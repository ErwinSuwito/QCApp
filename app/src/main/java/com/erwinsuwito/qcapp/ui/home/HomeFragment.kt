package com.erwinsuwito.qcapp.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.adapter.IssueCardAdapter
import com.erwinsuwito.qcapp.adapter.TaskCardAdapter
import com.erwinsuwito.qcapp.model.Classroom
import com.erwinsuwito.qcapp.model.Issue
import com.erwinsuwito.qcapp.model.Task
import com.erwinsuwito.qcapp.ui.classrooms.ClassDetailActivity
import com.erwinsuwito.qcapp.ui.issues.IssueDetailActivity
import com.google.android.material.chip.Chip
import java.time.LocalDate
import java.time.LocalDateTime

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        var dummyTasksList = mutableListOf(
            Task("D", "B-08-09", "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo" , "The projector can't project from any sources. Long HDMI, Short HDMI and EasyMP projection doesn't work.", "All projection doesn't work", LocalDateTime.now(),  LocalDateTime.now(), "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo", false),
            Task("D", "B-08-09", "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo" , "The projector can't project from any sources. Long HDMI, Short HDMI and EasyMP projection doesn't work.", "All projection doesn't work", LocalDateTime.now(),  LocalDateTime.now(), "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo", true),
            Task("D", "B-08-09", "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo" , "The projector can't project from any sources. Long HDMI, Short HDMI and EasyMP projection doesn't work.", "All projection doesn't work", LocalDateTime.now(),  LocalDateTime.now(), "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo", true),
            Task("D", "B-08-09", "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo" , "The projector can't project from any sources. Long HDMI, Short HDMI and EasyMP projection doesn't work.", "All projection doesn't work", LocalDateTime.now(),  LocalDateTime.now(), "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo", true)
        )

        var dummyIssuesList = mutableListOf(
            Issue("D", "D-08-09", "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo", "The projector can't project from any sources. Long HDMI, Short HDMI and EasyMP projection doesn't work.", LocalDateTime.now(), LocalDateTime.now(), "erwin.suwito@cloudmails.apu.edu.my", false),
            Issue("D", "D-08-09", "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo", "The projector can't project from any sources. Long HDMI, Short HDMI and EasyMP projection doesn't work.", LocalDateTime.now(), LocalDateTime.now(), "erwin.suwito@cloudmails.apu.edu.my", false),
            Issue("D", "D-08-09", "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo", "The projector can't project from any sources. Long HDMI, Short HDMI and EasyMP projection doesn't work.", LocalDateTime.now(), LocalDateTime.now(), "erwin.suwito@cloudmails.apu.edu.my", false),
        )

        val task_card_list = root.findViewById<RecyclerView>(R.id.task_card_list)
        task_card_list.adapter = TaskCardAdapter(root.context, dummyTasksList, { issue -> taskItemClicked(root.context, issue)})

        val issues_card_list = root.findViewById<RecyclerView>(R.id.issues_card_list)
        issues_card_list.adapter = IssueCardAdapter(root.context, dummyIssuesList, true, { issue -> issueItemClicked(root.context, issue)})

        val B_06_08Chip = root.findViewById<Chip>(R.id.B_06_08Chip)
        val B_06_10Chip = root.findViewById<Chip>(R.id.B_06_10Chip)

        B_06_08Chip.setOnClickListener {
            val intent = Intent(activity, ClassDetailActivity::class.java)
            intent.putExtra("class", Classroom("B-06-08", "AP-P001", "192.168.1.2", 5000, 100, true, LocalDateTime.now()))
            activity?.startActivity(intent)
        }

        B_06_10Chip.setOnClickListener {
            val intent = Intent(activity, ClassDetailActivity::class.java)
            intent.putExtra("class", Classroom("B-06-10", "AP-P001", "192.168.1.2", 5000, 100, false, LocalDateTime.now()))
            activity?.startActivity(intent)
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun taskItemClicked(context: Context, taskItem: Task)
    {
        Toast.makeText(context, "A task is clicked.", Toast.LENGTH_SHORT).show()
        //requireView().findNavController().navigate(R.id.action_navigation_home_to_classDetailFragment)
    }

    fun issueItemClicked(context: Context, issueItem: Issue)
    {
        val intent = Intent(activity, IssueDetailActivity::class.java)
        activity?.startActivity(intent)
    }
}