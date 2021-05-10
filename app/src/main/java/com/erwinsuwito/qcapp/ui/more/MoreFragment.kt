package com.erwinsuwito.qcapp.ui.more

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.adapter.MoreItemsAdapter
import com.erwinsuwito.qcapp.apis.AuthenticationHelper
import com.erwinsuwito.qcapp.model.MoreItem
import com.erwinsuwito.qcapp.ui.login.LoginActivity

class MoreFragment : Fragment() {

    private lateinit var moreViewModel: MoreViewModel
    var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        moreViewModel =
                ViewModelProvider(this).get(MoreViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_more, container, false)

        var moreItems = mutableListOf<MoreItem>(
            MoreItem(R.string.ta_portal, R.drawable.ic_sharepoint),
            MoreItem(R.string.trainee_portal, R.drawable.ic_sharepoint),
            MoreItem(R.string.planner, R.drawable.ic_planner),
            MoreItem(R.string.teams, R.drawable.ic_teams),
            MoreItem(R.string.logout, R.drawable.ic_logout_24)
        )

        sharedPreferences = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        if (sharedPreferences!!.getString("usr_role", "Technical Assistant") == "Trainee")
        {
            moreItems.removeAt(0)
        }
        else if (sharedPreferences!!.getString("usr_role", "Trainee") == "Trainee")
        {
            moreItems.removeAt(1)
        }

        val recyclerView = root.findViewById<RecyclerView>(R.id.moreActionsListView)
        recyclerView.adapter = MoreItemsAdapter(this, moreItems, { moreItem -> moreItemClicked(root.context, moreItem)})

        recyclerView.setHasFixedSize(true)

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            1
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        val userNameTextView = root.findViewById<TextView>(R.id.user_name)
        userNameTextView.text = sharedPreferences?.getString("usr_name", "User")
        val userEmailTextView = root.findViewById<TextView>(R.id.user_email)
        userEmailTextView.text = sharedPreferences?.getString("upn", "someone@cloudmails.apu.edu.my")
        val roleTextView = root.findViewById<TextView>(R.id.user_role)
        roleTextView.text = sharedPreferences?.getString("usr_role", "Technical Assistant")

        return root
    }

    private fun moreItemClicked(context: Context, moreItem: MoreItem)
    {
        var action: String = getString(moreItem.actionId)
        //Toast.makeText(context, "$string is clicked.", Toast.LENGTH_SHORT).show()
        when (action) {
            "Logout" -> {
                //var authHelper: AuthenticationHelper = AuthenticationHelper.getInstance()
                //authHelper.signOut()

                val intent = Intent(activity, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
            "Open TA Portal" -> {
                // TO-DO: 
                // Code taken from:
                // https://www.tutorialspoint.com/how-to-open-a-website-in-android-s-web-browser-from-my-application-using-kotlin
                val url = getString(R.string.li_ta_portal)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
            "Open Trainee Portal" -> {
                val url = getString(R.string.li_trainee_portal)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
            "Open Planner" -> {
                val url = getString(R.string.li_planner)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
            "Open Microsoft Teams" -> {
                val url = getString(R.string.li_teams)
                val teamsIntent = getString(R.string.intent_teams)
                try
                {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(teamsIntent)))
                }
                catch (e: Exception)
                {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }

            }
            // TO-DO: Move this code to where Chat on Teams option is available
            "Chat on Teams" -> {
                var users: String = "TP045000@mail.apu.edu.my" // Append all chat participants with ","
                val url = getString(R.string.teams_chat_link).replace("|users", users)
                val teamsIntent = getString(R.string.teams_chat_intent).replace("|users", users)

                try
                {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(teamsIntent)))
                }
                catch (e: Exception)
                {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
            }
            else -> {
                Toast.makeText(context, "$action is clicked.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}