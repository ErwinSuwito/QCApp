package com.erwinsuwito.qcapp.ui.qc

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.erwinsuwito.qcapp.App
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.apis.FirebaseIDGenerator
import com.erwinsuwito.qcapp.apis.FirestoreHelper
import com.erwinsuwito.qcapp.model.Task
import com.google.android.material.textfield.TextInputEditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTaskFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTaskFragment : Fragment() {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_task, container, false)

        val sharedPreferences = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val usrName = sharedPreferences!!.getString("usr_name", "User")
        val upn = sharedPreferences!!.getString("upn", "someone@cloudmails.apu.edu.my")
        val task_TaskTitleTextBox = root.findViewById<TextInputEditText>(R.id.task_TaskTitleTextBox)
        val task_TaskDetailTextBox = root.findViewById<TextInputEditText>(R.id.task_TaskDetailTextBox)
        val saveBtn = root.findViewById<Button>(R.id.task_saveBtn)

        saveBtn.setOnClickListener {
            var task = Task(FirebaseIDGenerator.generateId(), upn!!, usrName!!, task_TaskDetailTextBox.text.toString(), task_TaskTitleTextBox.text.toString())
            FirestoreHelper().addTask(task, {onSuccess()}, {onFailure()})
        }

        return root
    }

    fun onSuccess()
    {
        Toast.makeText(App.context, getString(R.string.issue_added), Toast.LENGTH_SHORT).show()
        activity?.onBackPressed()
    }

    fun onFailure()
    {
        val builder = AlertDialog.Builder(App.context!!)
        builder.setTitle(R.string.unable_add_issue)
        builder.setMessage(R.string.unable_add_issue_message)
        builder.setPositiveButton(R.string.okay) { dialog, which ->

        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddTaskFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                AddTaskFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}