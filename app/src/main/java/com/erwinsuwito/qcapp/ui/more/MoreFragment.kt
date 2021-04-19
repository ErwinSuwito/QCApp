package com.erwinsuwito.qcapp.ui.more

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.AppState
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.adapter.MoreItemsAdapter
import com.erwinsuwito.qcapp.model.MoreItem
import kotlinx.android.synthetic.main.fragment_more.*

class MoreFragment : Fragment() {

    private lateinit var moreViewModel: MoreViewModel

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

        val moreItems = listOf<MoreItem>(
            MoreItem(R.string.ta_portal, R.drawable.ic_sharepoint),
            MoreItem(R.string.trainee_portal, R.drawable.ic_sharepoint),
            MoreItem(R.string.planner, R.drawable.ic_planner),
            MoreItem(R.string.teams, R.drawable.ic_teams),
            MoreItem(R.string.logout, R.drawable.ic_logout_24),
        )

        val recyclerView = root.findViewById<RecyclerView>(R.id.moreActionsListView)
        recyclerView.adapter = MoreItemsAdapter(this, moreItems)
        recyclerView.setHasFixedSize(true)

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            1
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        val userNameTextView = root.findViewById<TextView>(R.id.user_name)
        userNameTextView.text = AppState.fullName
        val userEmailTextView = root.findViewById<TextView>(R.id.user_email)
        userEmailTextView.text = AppState.upn
        val roleTextView = root.findViewById<TextView>(R.id.user_role)
        roleTextView.text = AppState.role

        return root
    }

    private fun moreItemClicked(context: Context, moreItem: MoreItem)
    {
        Toast.makeText(context, "More item ${moreItem.actionId} is clicked.", Toast.LENGTH_SHORT).show()
    }
}