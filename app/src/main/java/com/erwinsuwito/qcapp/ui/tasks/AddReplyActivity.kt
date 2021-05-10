package com.erwinsuwito.qcapp.ui.tasks

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.erwinsuwito.qcapp.App
import com.erwinsuwito.qcapp.BaseActivity
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.apis.FirebaseIDGenerator
import com.erwinsuwito.qcapp.apis.FirestoreHelper
import com.erwinsuwito.qcapp.model.Issue
import com.erwinsuwito.qcapp.model.Steps
import com.erwinsuwito.qcapp.model.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Timestamp

class AddReplyActivity : BaseActivity() {
    var itemType = 0
    var selectedTask: Task? = null
    var selectedIssue: Issue? = null
    var closingItem = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reply)

        itemType = intent.getIntExtra("itemType", 0)

        when (itemType)
        {
            0 -> finish()
            1 -> {
                selectedTask = intent.getParcelableExtra("task")
            }
            2 -> {
                selectedIssue = intent.getParcelableExtra("issue")
            }
        }

        var taskAddReply_SaveBtn: Button = findViewById(R.id.issueAddReply_SaveBtn)
        taskAddReply_SaveBtn.setOnClickListener {
            val sharedPreferences = this.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val usrName = sharedPreferences!!.getString("usr_name", "User")
            val upn = sharedPreferences.getString("upn", "someone@cloudmails.apu.edu.my")
            val content = findViewById<TextInputEditText>(R.id.issue_replyTextBox).text.toString()
            closingItem = findViewById<CheckBox>(R.id.issueAddReply_closeCheckBox).isChecked

            when (itemType)
            {
                1 -> {
                    var reply = Steps(FirebaseIDGenerator.generateId(), upn!!, usrName!!, content, Timestamp.now(), closingItem)
                    FirestoreHelper().addTaskSteps(selectedTask!!, reply, {onSuccess()}, {onFailed()})

                    if (closingItem) {
                        var closedBy = this.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("upn", "someone@cloudmails.apu.edu.my")
                        var closedByName = this.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("usr_name", "User")

                        selectedTask!!.isOpen = true
                        selectedTask!!.closedBy = closedBy!!
                        selectedTask!!.closedByName = closedByName!!
                        selectedTask!!.closedOn = Timestamp.now()
                    }
                }
                2 -> {
                    var reply = Steps(FirebaseIDGenerator.generateId(), upn!!, usrName!!, content, Timestamp.now(), closingItem)
                    FirestoreHelper().addIssueSteps(selectedIssue!!, reply, {onSuccess()}, {onFailed()})

                    if (closingItem) {
                        var closedBy = this.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("upn", "someone@cloudmails.apu.edu.my")
                        var closedByName = this.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("usr_name", "User")

                        selectedIssue!!.isOpen = true
                        selectedIssue!!.closedBy = closedBy!!
                        selectedIssue!!.closedByName = closedByName!!
                        selectedIssue!!.closedOn = Timestamp.now()
                    }

                }

            }
        }
    }

    fun onSuccess() {
        if (closingItem) {
            closingItem = false
            when (itemType) {
                1 -> {
                    FirestoreHelper().addTask(selectedTask!!, {onSuccess()}, {updateTaskFailed()})
                }
                2 -> {
                    FirestoreHelper().addIssue(selectedIssue!!, {onSuccess()}, {updateTaskFailed()})
                }
            }
        }
        else {
            Toast.makeText(this, "Reply added successfully!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun onFailed() {
        Toast.makeText(this, "Unable to add reply", Toast.LENGTH_SHORT).show()
    }

    fun updateTaskFailed() {
        val builder = AlertDialog.Builder(App.context!!)
        builder.setTitle("Completed with failure")
        builder.setMessage("Your reply has been saved successfully. However, the issue or task was not closed successfully. Please notify a board member to close the issue manually.")
        builder.setPositiveButton(R.string.okay) { dialog, which ->
            finish()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}