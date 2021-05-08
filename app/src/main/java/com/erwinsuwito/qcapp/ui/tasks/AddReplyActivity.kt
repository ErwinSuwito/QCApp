package com.erwinsuwito.qcapp.ui.tasks

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
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

            when (itemType)
            {
                1 -> {
                    var reply = Steps(FirebaseIDGenerator.generateId(), upn!!, usrName!!, content, Timestamp.now())
                    FirestoreHelper().addTaskSteps(selectedTask!!, reply, {onSuccess()}, {onFailed()})
                }
                2 -> {
                    var reply = Steps(FirebaseIDGenerator.generateId(), upn!!, usrName!!, content, Timestamp.now())
                    FirestoreHelper().addIssueSteps(selectedIssue!!, reply, {onSuccess()}, {onFailed()})
                }

            }
        }
    }

    fun onSuccess() {
        Toast.makeText(this, "Reply added successfully!", Toast.LENGTH_SHORT).show()
        finish()
    }

    fun onFailed() {
        Toast.makeText(this, "Unable to add reply", Toast.LENGTH_SHORT).show()
    }
}