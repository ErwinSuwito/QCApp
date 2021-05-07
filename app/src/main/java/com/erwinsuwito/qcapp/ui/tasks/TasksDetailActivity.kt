package com.erwinsuwito.qcapp.ui.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.erwinsuwito.qcapp.BaseActivity
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.apis.FirestoreHelper
import com.erwinsuwito.qcapp.model.Steps
import com.erwinsuwito.qcapp.model.Task
import kotlinx.android.synthetic.main.activity_tasks_detail.*

class TasksDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_detail)

        val selectedTask: Task? = intent.extras?.getParcelable("task")

        if (selectedTask != null)
        {
            showProgressDialog()
            task_added_by_avatar.name = selectedTask.creatorName
            task_added_by_name.text = selectedTask.creatorName
            taskDetail_taskBody.text = selectedTask.taskDetails
            taskDetail_taskTitle.text = selectedTask.taskTitle
            taskDetail_addedOn.text = selectedTask.openedOn.toDate().toLocaleString()
            if (!selectedTask.isOpen)
            {
                taskDetail_closedOnPanel.visibility = View.VISIBLE
                taskDetail_closedOn.text = selectedTask.closedOn.toDate().toLocaleString()
            }

            FirestoreHelper().getTaskSteps(selectedTask.issueId, {displaySteps(it)}, {onFailure()})
        }
        else
        {
            finish()
        }
    }

    fun displaySteps(steps: MutableList<Steps>)
    {
        // TO-DO: Add code here to show retrieved data to the UI
    }

    fun onReplyClicked(reply: Steps)
    {

    }

    fun onFailure() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Unable to get task details")
        builder.setMessage("We're unable to get the requested task details. Please try again later.")
        builder.setPositiveButton(R.string.okay) { dialog, which ->
            finish()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}