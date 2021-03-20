package com.erwinsuwito.qcapp.ui.login

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.erwinsuwito.qcapp.MainActivity
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.graph.GraphHelper
import com.erwinsuwito.qcapp.graph.AuthenticationHelper
import com.microsoft.graph.concurrency.ICallback
import com.microsoft.graph.core.ClientException
import com.microsoft.graph.models.extensions.User
import com.microsoft.identity.client.AuthenticationCallback
import com.microsoft.identity.client.IAuthenticationResult
import com.microsoft.identity.client.exception.MsalClientException
import com.microsoft.identity.client.exception.MsalException
import com.microsoft.identity.client.exception.MsalServiceException
import com.microsoft.identity.client.exception.MsalUiRequiredException

private const val SAVED_IS_SIGNED_IN = "isSignedIn"

class LoginActivity : AppCompatActivity() {
    private var isSignedIn = false
    private lateinit var authHelper: AuthenticationHelper
    private var attemptInteractiveSignIn: Boolean = false

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
            }, 500)
        }
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

            findViewById<LinearLayout>(R.id.linearLayout).isVisible = true
            findViewById<Button>(R.id.button).isVisible = true
            attemptInteractiveSignIn = true
        }
    }

    private fun getUserCallback(): ICallback<User> = object : ICallback<User> {
        override fun success(result: User?) {
            result?.apply {
                Log.d("AUTH", "User: $displayName")
            }
            updateUI()
        }

        override fun failure(ex: ClientException?) {
            Log.e("AUTH", "Error getting /me", ex)
            updateUI()
        }

        private fun updateUI() = runOnUiThread {
            if (isSignedIn)
            {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else
            {
                attemptInteractiveSignIn = true
                val layout: LinearLayout = findViewById(R.id.linearLayout)
                layout.isVisible = true
                val loginButton: Button = findViewById(R.id.button)
                loginButton.isVisible = true
            }
        }
    }
}