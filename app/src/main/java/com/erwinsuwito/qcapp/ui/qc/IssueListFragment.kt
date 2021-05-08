package com.erwinsuwito.qcapp.ui.qc

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.App
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.adapter.IssueCardAdapter
import com.erwinsuwito.qcapp.apis.FirestoreHelper
import com.erwinsuwito.qcapp.model.Issue
import com.erwinsuwito.qcapp.ui.issues.IssueDetailActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_issue_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [IssueListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class IssueListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var rootFab: FloatingActionButton
    lateinit var no_issues_panel: LinearLayout
    lateinit var issueList_recylerView: RecyclerView
    lateinit var issue_progressBar: ProgressBar
    lateinit var party_icon: ImageView
    lateinit var noIssues_Text: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_issue_list, container, false)

        no_issues_panel = root.findViewById(R.id.no_issues_panel)
        issueList_recylerView = root.findViewById(R.id.issueList_recyclerView)
        issue_progressBar = root.findViewById(R.id.issue_progressBar)
        party_icon = root.findViewById(R.id.party_icon)
        noIssues_Text = root.findViewById(R.id.noIssues_Text)

        rootFab = root.findViewById(R.id.addIssueFab)
        rootFab.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_qc_to_addIssueFragment)
        }

        FirestoreHelper().getIssueList({onSuccess(it)}, {onFail()})

        return root
    }

    fun onSuccess(issueList: MutableList<Issue>) {
        issue_progressBar.visibility = View.GONE
        if (issueList.count() < 1) {
            no_issues_panel.visibility = View.VISIBLE
        }
        else {
            issueList_recylerView.visibility = View.VISIBLE
            issueList_recylerView.adapter = IssueCardAdapter(App.context!!, issueList, true, { issue -> onItemClick(issue)})
        }
    }

    fun onFail() {
        party_icon.visibility = View.GONE
        noIssues_Text.text = getString(R.string.unable_display_issue_list)
        no_issues_panel.visibility = View.VISIBLE
    }

    fun onItemClick(issue: Issue) {
        val intent = Intent(activity, IssueDetailActivity::class.java)
        intent.extras!!.putParcelable("issue", issue)
        activity?.startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment IssueListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            IssueListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}