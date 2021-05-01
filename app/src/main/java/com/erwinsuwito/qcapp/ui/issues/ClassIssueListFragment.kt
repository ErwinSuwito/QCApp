package com.erwinsuwito.qcapp.ui.issues

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.adapter.IssueCardAdapter
import com.erwinsuwito.qcapp.model.Issue
import java.time.LocalDateTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClassIssueListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClassIssueListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val root = inflater.inflate(R.layout.fragment_class_issue_list, container, false)

        var dummyIssuesList = mutableListOf(
                Issue("D-08-09", "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo", "The projector can't project from any sources. Long HDMI, Short HDMI and EasyMP projection doesn't work.", LocalDateTime.now(), false),
                Issue("D-08-09", "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo", "The projector can't project from any sources. Long HDMI, Short HDMI and EasyMP projection doesn't work.", LocalDateTime.now(), false),
                Issue("D-08-09", "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo", "The projector can't project from any sources. Long HDMI, Short HDMI and EasyMP projection doesn't work.", LocalDateTime.now(), false)
        )

        val issues_recyclerview_classIssues = root.findViewById<RecyclerView>(R.id.issues_recyclerview_classIssues)
        issues_recyclerview_classIssues.adapter = IssueCardAdapter(root.context, dummyIssuesList, { issue -> itemClicked() } )

        return root
    }

    fun itemClicked()
    {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClassIssueListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ClassIssueListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}