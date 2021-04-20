package com.erwinsuwito.qcapp.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.erwinsuwito.qcapp.AppState
import com.erwinsuwito.qcapp.MainActivity
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.apis.AuthenticationHelper
import com.erwinsuwito.qcapp.apis.GraphHelper
import com.microsoft.graph.concurrency.ICallback
import com.microsoft.graph.core.ClientException
import com.microsoft.graph.models.extensions.Team
import com.microsoft.graph.models.extensions.User
import com.microsoft.graph.requests.extensions.ITeamCollectionPage
import com.microsoft.identity.client.AuthenticationCallback
import com.microsoft.identity.client.IAuthenticationResult
import com.microsoft.identity.client.exception.MsalClientException
import com.microsoft.identity.client.exception.MsalException
import com.microsoft.identity.client.exception.MsalServiceException
import com.microsoft.identity.client.exception.MsalUiRequiredException


private const val SAVED_IS_SIGNED_IN = "isSignedIn"

class LoginActivity : AppCompatActivity() {
    private var isSignedIn = false
    private var isAllowedSignIn = false
    private lateinit var authHelper: AuthenticationHelper
    private var attemptInteractiveSignIn: Boolean = false
    private lateinit var sharedPreferences: SharedPreferences.Editor

    private var teamList: MutableList<Team>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authHelper = AuthenticationHelper.getInstance(applicationContext)

        val loginButton: Button = findViewById(R.id.button)
        loginButton.setOnClickListener()
        {
            signIn()
        }

        savedInstanceState?.apply {
            isSignedIn = getBoolean(SAVED_IS_SIGNED_IN)
        } ?: run {
            val handler = Handler().postDelayed({
                doSilentSignIn()
            }, 2000)
        }

        sharedPreferences = this.getSharedPreferences("prefs", Context.MODE_PRIVATE).edit()
    }

    override fun onSaveInstanceState(outState: Bundle) = with(outState) {
        super.onSaveInstanceState(this)
        putBoolean(SAVED_IS_SIGNED_IN, isSignedIn)
    }

    private fun signIn() {
        doSilentSignIn()
    }

    // Silently sign in - used if there is already a
    // user account in the MSAL cache
    private fun doSilentSignIn() = authHelper.acquireTokenSilently(getAuthCallback())

    // Prompt the user to sign in
    private fun doInteractiveSignIn() =
            authHelper.acquireTokenInteractively(this, getAuthCallback())

    // Handles the authentication result
    private fun getAuthCallback(): AuthenticationCallback = object : AuthenticationCallback {
        override fun onSuccess(authenticationResult: IAuthenticationResult?) =
                authenticationResult?.accessToken.let {
                    // Log the token for debug purposes
                    Log.d("AUTH", String.format("Access token: %s", it))

                    // Get Graph client and get user
                    GraphHelper.getInstance().getUser(it!!, getUserCallback())

                    // Get the groups of the user
                    GraphHelper.getInstance().getJoinedTeams(it!!, getJoinedTeamsCallback())

                    isSignedIn = true;
                }

        override fun onCancel() {
            // User canceled the authentication
            Log.d("AUTH", "Authentication canceled")
            findViewById<TextView>(R.id.textView4).text = "Sorry that didn't work. Please try again."
        }

        override fun onError(exception: MsalException?): Unit = with(exception) {
            // Check the type of "this" exception and handle appropriately
            if (this is MsalUiRequiredException) {
                Log.d("AUTH", "Interactive login required")
                if (attemptInteractiveSignIn) doInteractiveSignIn()
            } else if (this is MsalClientException) {
                if (errorCode == "no_current_account") {
                    Log.d("AUTH", "No current account, interactive login required")
                    if (attemptInteractiveSignIn) doInteractiveSignIn()
                } else {
                    // Exception inside MSAL, more info inside MsalError.java
                    Log.e("AUTH", "Client error authenticating", exception)
                }
            } else if (this is MsalServiceException) {
                // Exception when communicating with the auth server, likely config issue
                Log.e("AUTH", "Service error authenticating", exception)
            }

            findViewById<LinearLayout>(R.id.welcomeLayout).isVisible = true
            findViewById<Button>(R.id.button).isVisible = true
            attemptInteractiveSignIn = true
        }
    }

    private fun getJoinedTeamsCallback(): ICallback<ITeamCollectionPage> = object : ICallback<ITeamCollectionPage> {
        override fun success(result: ITeamCollectionPage?) {
            teamList = result?.currentPage

            run breaker@ {
                teamList?.forEach {
                    when {
                        it.displayName.equals("Board Members") -> {
                            sharedPreferences.putString("usr_role", getString(R.string.board))
                            AppState.role = "Board Member"
                            isAllowedSignIn = true
                            return@breaker
                        }
                        it.displayName.equals("APU-Technical Assistant") -> {
                            AppState.role = "Technical Assistant"
                            sharedPreferences.putString("usr_role", getString(R.string.ta))
                            isAllowedSignIn = true
                            return@breaker
                        }
                        it.displayName.contains("TA - Trainee") -> {
                            sharedPreferences.putString("usr_role", getString(R.string.trainee))
                            isAllowedSignIn = true
                            return@breaker
                        }
                        it.displayName.equals("Sandbox") -> {
                            sharedPreferences.putString("usr_role", getString(R.string.trainee))
                            isAllowedSignIn = true
                            return@breaker
                        }
                    }
                }
            }


            if (!isAllowedSignIn)
            {
                authHelper.signOut()
                isSignedIn = false
            }

            updateUI()
        }

        override fun failure(ex: ClientException?) {
            Log.e("GRAPH", "Error getting teams", ex)
            authHelper.signOut()
            isSignedIn = false
            updateUI()
        }
    }

    private fun updateUI() = runOnUiThread {
        if (isSignedIn)
        {
            sharedPreferences.apply()
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else
        {
            if (!isAllowedSignIn)
            {
                // https://www.geeksforgeeks.org/android-alert-dialog-box-and-how-to-create-it/#:~:text=Below%20are%20the%20steps%20for%20Creating%20the%20Alert,back%20button%20of%20your%20device.%20More%20items...%20
                val builder = AlertDialog.Builder(this)
                builder.setMessage(R.string.acc_not_authorized_message)
                builder.setTitle(R.string.acc_not_authorized_header)
                builder.setPositiveButton(R.string.okay) { dialog, which ->

                }
                val alertDialog = builder.create()
                alertDialog.show()
            }

            attemptInteractiveSignIn = true
            val layout: LinearLayout = findViewById(R.id.welcomeLayout)
            layout.isVisible = true
            val loginButton: Button = findViewById(R.id.button)
            loginButton.isVisible = true
        }
    }

    private fun getUserCallback(): ICallback<User> = object : ICallback<User> {
        override fun success(result: User?) {
            result?.apply {
                Log.d("AUTH", "User: $displayName")
                sharedPreferences.putString("usr_name", result.displayName)
                sharedPreferences.putString("upn", result.userPrincipalName)
            }
        }

        override fun failure(ex: ClientException?) {
            Log.e("AUTH", "Error getting /me", ex)
            if (ex != null) {
                // !! is a not null assertion. Use only when you're sure something is not going to be null
                if (ex.message!!.contains("Connection is not available to refresh token")) {
                    findViewById<LinearLayout>(R.id.welcomeLayout).isVisible = true
                    findViewById<TextView>(R.id.textView4).text = "You're offline."
                }
            }
            updateUI()
        }
    }

}