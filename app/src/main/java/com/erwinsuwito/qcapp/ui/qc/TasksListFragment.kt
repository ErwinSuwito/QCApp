package com.erwinsuwito.qcapp.ui.qc

import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.App
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.adapter.TaskCardAdapter
import com.erwinsuwito.qcapp.apis.FirestoreHelper
import com.erwinsuwito.qcapp.model.Task
import com.erwinsuwito.qcapp.ui.tasks.TasksDetailActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TasksListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TasksListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var task_progressBar: ProgressBar
    lateinit var no_tasks_textView: TextView
    lateinit var taskList_RecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_tasks_list, container, false)

        val rootFab = root.findViewById<FloatingActionButton>(R.id.addTaskFabs)
        task_progressBar = root.findViewById(R.id.task_progressBar)
        no_tasks_textView = root.findViewById(R.id.no_tasks_textView)
        taskList_RecyclerView = root.findViewById(R.id.taskList_RecyclerView)

        val sharedPreferences = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        if (sharedPreferences!!.getString("usr_role", "Technical Assistant") != "Board Member")
        {
            rootFab.visibility = View.GONE
        }

        rootFab.setOnClickListener{
            findNavController().navigate(R.id.action_navigation_qc_to_addTaskFragment2)
        }

        FirestoreHelper().getTasksList({onSuccess(it)}, {onFail()})

        return root
    }

    fun onSuccess(taskList: MutableList<Task>) {
        task_progressBar.visibility = View.GONE
        if (taskList.count() < 1) {
            no_tasks_textView.visibility = View.VISIBLE
        }
        else {
            taskList_RecyclerView.visibility = View.VISIBLE
            taskList_RecyclerView.adapter = TaskCardAdapter(App.context!!, taskList, { task -> onItemClick(task)})
        }
    }

    fun onFail() {
        task_progressBar.visibility = View.GONE
        no_tasks_textView.text = getString(R.string.unable_display_task_list)
        no_tasks_textView.visibility = View.VISIBLE
    }

    fun onItemClick(task: Task) {
        val intent = Intent(activity, TasksDetailActivity::class.java).putExtra("task", task)
        activity?.startActivity(intent)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TasksListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TasksListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}