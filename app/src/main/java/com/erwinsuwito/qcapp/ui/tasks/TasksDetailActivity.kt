package com.erwinsuwito.qcapp.ui.tasks

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.erwinsuwito.qcapp.BaseActivity
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.adapter.StepsAdapter
import com.erwinsuwito.qcapp.apis.FirestoreHelper
import com.erwinsuwito.qcapp.model.Steps
import com.erwinsuwito.qcapp.model.Task
import com.erwinsuwito.qcapp.ui.issues.IssueDetailActivity
import com.google.firebase.Timestamp
import com.microsoft.fluentui.bottomsheet.BottomSheet
import com.microsoft.fluentui.bottomsheet.BottomSheetItem
import kotlinx.android.synthetic.main.activity_tasks_detail.*

class TasksDetailActivity : BaseActivity(), BottomSheetItem.OnClickListener {
    var selectedTask: Task? = null
    lateinit var usrName: String
    lateinit var upn: String
    lateinit var role: String
    lateinit var participant_upn: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_detail)

        selectedTask = intent.extras?.getParcelable("task")

        if (selectedTask != null)
        {
            showProgressDialog()
            task_added_by_avatar.name = selectedTask!!.creatorName
            task_added_by_name.text = selectedTask!!.creatorName
            taskDetail_taskBody.text = selectedTask!!.taskDetails
            taskDetail_taskTitle.text = selectedTask!!.taskTitle
            taskDetail_addedOn.text = selectedTask!!.openedOn.toDate().toLocaleString()
            if (!selectedTask!!.isOpen)
            {
                taskDetail_closedOnPanel.visibility = View.VISIBLE
                taskDetail_closedOn.text = selectedTask!!.closedOn.toDate().toLocaleString()
            }

            FirestoreHelper().getTaskSteps(selectedTask!!.issueId, {displaySteps(it)}, {onFailure()})

            issue_OverflowBtn.setOnClickListener {onOverflowButtonClicked()}
        }
        else
        {
            finish()
        }
    }

    fun displaySteps(steps: MutableList<Steps>)
    {
        taskDetail_repliesList.adapter = StepsAdapter(this, steps, {onReplyClicked(it)})
        hideProgressDialog()
    }

    fun onOverflowButtonClicked() {
        val sharedPreferences = this.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        usrName = sharedPreferences.getString("usr_name", "User")!!
        upn = sharedPreferences.getString("upn", "someone@cloudmails.apu.edu.my")!!
        role = sharedPreferences.getString("usr_role", "Technical Assistant")!!

        var bottomSheetItems = arrayListOf(
                BottomSheetItem(
                        R.id.bottom_sheet_item_reply,
                        R.drawable.ic_chat,
                        getString(R.string.reply)
                )
        )

        if (role == "Board Member")
        {
            if (selectedTask!!.isOpen)
            {
                bottomSheetItems.add(BottomSheetItem(R.id.bottom_sheet_mark_close, R.drawable.ic_complete, "Close task"))
            }
            else
            {
                bottomSheetItems.add(BottomSheetItem(R.id.bottom_sheet_mark_re_open, R.drawable.ic_giftbox_open, "Re-open task"))
            }
        }

        val bottomSheet = BottomSheet.newInstance(bottomSheetItems)
        bottomSheet.show(supportFragmentManager, null)
    }

    fun onReplyClicked(reply: Steps)
    {
        participant_upn = reply.author

        var bottomSheetItems = arrayListOf(
                BottomSheetItem(
                        R.id.bottom_sheet_item_reply,
                        R.drawable.ic_chat,
                        getString(R.string.reply)
                ),
                BottomSheetItem(
                        R.id.bottom_sheet_teams_pm_participant,
                        R.drawable.ic_teams,
                        getString(R.string.chat_teams)
                )
        )

        val bottomSheet = BottomSheet.newInstance(bottomSheetItems)
        bottomSheet.show(supportFragmentManager, null)
    }

    fun onFailure() {
        hideProgressDialog()
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Unable to get task details")
        builder.setMessage("We're unable to get the requested task details. Please try again later.")
        builder.setPositiveButton(R.string.okay) { dialog, which ->
            finish()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onBottomSheetItemClick(item: BottomSheetItem) {
        when (item.id) {
            R.id.bottom_sheet_item_reply -> {
                val intent = Intent(this, AddReplyActivity::class.java).putExtra("task", selectedTask).putExtra("itemType", 1)
                this.startActivity(intent)
            }

            R.id.bottom_sheet_teams_pm_starter -> {
                val url = getString(R.string.teams_chat_link).replace("|users", selectedTask!!.creator)
                val teamsIntent = getString(R.string.teams_chat_intent).replace("|users", selectedTask!!.creatorName)

                try
                {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(teamsIntent)))
                }
                catch (e: Exception)
                {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
            }

            R.id.bottom_sheet_teams_pm_participant -> {
                val url = getString(R.string.teams_chat_link).replace("|users", participant_upn)
                val teamsIntent = getString(R.string.teams_chat_intent).replace("|users", participant_upn)

                try
                {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(teamsIntent)))
                }
                catch (e: Exception)
                {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                }
            }

            R.id.bottom_sheet_mark_re_open -> {
                selectedTask!!.isOpen = true
                updateTask()
            }

            R.id.bottom_sheet_mark_close -> {
                selectedTask!!.isOpen = false
                selectedTask!!.closedOn = Timestamp.now()
                selectedTask!!.closedBy = upn
                selectedTask!!.closedByName = usrName
                updateTask()
            }
        }
    }

    fun updateTask() {
        FirestoreHelper().addTask(selectedTask!!, {updateSuccess()}, {updateFailed()})
    }

    fun updateSuccess() {
        Toast.makeText(this, "Task updated", Toast.LENGTH_LONG).show()
    }

    fun updateFailed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Unable to close/re-open task")
        builder.setMessage("We're unable to update the task details. Please try again later.")
        builder.setPositiveButton(R.string.okay) { dialog, which ->
            finish()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}