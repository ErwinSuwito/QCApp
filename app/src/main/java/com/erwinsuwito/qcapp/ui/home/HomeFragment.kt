package com.erwinsuwito.qcapp.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.App
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.adapter.IssueCardAdapter
import com.erwinsuwito.qcapp.adapter.TaskCardAdapter
import com.erwinsuwito.qcapp.model.Classroom
import com.erwinsuwito.qcapp.model.Issue
import com.erwinsuwito.qcapp.model.Task
import com.erwinsuwito.qcapp.ui.classrooms.ClassDetailActivity
import com.erwinsuwito.qcapp.ui.issues.IssueDetailActivity
import com.erwinsuwito.qcapp.ui.tasks.TasksDetailActivity
import com.google.android.material.chip.Chip
import com.google.firebase.Timestamp
import java.time.LocalDate
import java.time.LocalDateTime

class HomeFragment : Fragment(), View.OnClickListener {

    private lateinit var homeViewModel: HomeViewModel
    var issues_card_list: RecyclerView? = null
    var task_card_list: RecyclerView? = null

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })


        issues_card_list = root.findViewById<RecyclerView>(R.id.issues_card_list)
        task_card_list = root.findViewById<RecyclerView>(R.id.task_card_list)

        val B_06_08Chip = root.findViewById<Chip>(R.id.B_06_08Chip)
        val B_06_10Chip = root.findViewById<Chip>(R.id.B_06_10Chip)
        val B_07_01Chip = root.findViewById<Chip>(R.id.B_07_01Chip)
        val B_07_02Chip = root.findViewById<Chip>(R.id.B_07_02Chip)
        val D_06_08Chip = root.findViewById<Chip>(R.id.D_06_08Chip)
        val D_06_10Chip = root.findViewById<Chip>(R.id.D_06_10Chip)

        B_06_08Chip.setOnClickListener(this)
        B_06_10Chip.setOnClickListener(this)
        B_07_01Chip.setOnClickListener(this)
        B_07_02Chip.setOnClickListener(this)
        D_06_08Chip.setOnClickListener(this)
        D_06_10Chip.setOnClickListener(this)

        val sharedPreferences = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        val usrNameTextView = root.findViewById<TextView>(R.id.home_username_textView)
        usrNameTextView.text = sharedPreferences!!.getString("usr_name", "User")

        return root
    }

    fun getTaskListSuccess(tasks: MutableList<Task>)
    {
        task_card_list!!.adapter = TaskCardAdapter(App.context!!, tasks, { task -> taskItemClicked(task)})
    }

    fun getIssueListSuccess(issues: MutableList<Issue>)
    {
        issues_card_list!!.adapter = IssueCardAdapter(App.context!!, issues, true, { issue -> issueItemClicked(issue)})
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    fun taskItemClicked(taskItem: Task)
    {
        val intent = Intent(activity, TasksDetailActivity::class.java)
        intent.extras!!.putParcelable("task", taskItem)
        activity?.startActivity(intent)
    }

    fun issueItemClicked(issueItem: Issue)
    {
        val intent = Intent(activity, IssueDetailActivity::class.java)
        intent.extras!!.putParcelable("issue", issueItem)
        activity?.startActivity(intent)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.B_06_08Chip -> {
                    val intent = Intent(activity, ClassDetailActivity::class.java)
                    intent.putExtra("class", "B-06-08")
                    activity?.startActivity(intent)
                }

                R.id.B_06_10Chip -> {
                    val intent = Intent(activity, ClassDetailActivity::class.java)
                    intent.putExtra("class", "B-06-10")
                    activity?.startActivity(intent)
                }

                R.id.B_07_01Chip -> {
                    val intent = Intent(activity, ClassDetailActivity::class.java)
                    intent.putExtra("class","B-07-01")
                    activity?.startActivity(intent)
                }

                R.id.B_07_02Chip -> {
                    val intent = Intent(activity, ClassDetailActivity::class.java)
                    intent.putExtra("class","B-07-02")
                    activity?.startActivity(intent)
                }

                R.id.D_06_08Chip -> {
                    val intent = Intent(activity, ClassDetailActivity::class.java)
                    intent.putExtra("class","D-06-08")
                    activity?.startActivity(intent)
                }

                R.id.D_06_10Chip -> {
                    val intent = Intent(activity, ClassDetailActivity::class.java)
                    intent.putExtra("class","D-06-10")
                    activity?.startActivity(intent)
                }
            }
        }
    }
}