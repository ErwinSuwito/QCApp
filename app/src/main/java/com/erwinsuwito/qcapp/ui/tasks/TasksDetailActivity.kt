package com.erwinsuwito.qcapp.ui.tasks

import android.content.Context
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
import com.microsoft.fluentui.bottomsheet.BottomSheet
import com.microsoft.fluentui.bottomsheet.BottomSheetItem
import kotlinx.android.synthetic.main.activity_tasks_detail.*

class TasksDetailActivity : BaseActivity(), BottomSheetItem.OnClickListener {
    var selectedTask: Task? = null

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
        }
        else
        {
            finish()
        }
    }

    fun displaySteps(steps: MutableList<Steps>)
    {
        taskDetail_repliesList.adapter = StepsAdapter(this, steps, {onReplyClicked(it)})
    }

    fun onReplyClicked(reply: Steps)
    {
        val sharedPreferences = this.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        var bottomSheetItems = arrayListOf(
                BottomSheetItem(
                        R.id.bottom_sheet_item_reply,
                        R.drawable.ic_chat,
                        getString(R.string.reply)
                ),
                BottomSheetItem(
                        R.id.bottom_sheet_teams_pm,
                        R.drawable.ic_teams,
                        getString(R.string.chat_teams)
                )
        )

        if (sharedPreferences!!.getString("usr_role", "Technical Assistant") == "Board Member")
        {
            if (selectedTask!!.isOpen)
            {
                bottomSheetItems.add(BottomSheetItem(R.id.bottom_sheet_mark_close, R.drawable.ic_complete, "Close issue"))
            }
            else
            {
                bottomSheetItems.add(BottomSheetItem(R.id.bottom_sheet_mark_re_open, R.drawable.ic_giftbox_open, "Re-open issue"))
            }
        }

        val bottomSheet = BottomSheet.newInstance(bottomSheetItems)
        bottomSheet.show(supportFragmentManager, null)
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

    override fun onBottomSheetItemClick(item: BottomSheetItem) {
        when (item.id) {
            // TO-DO: Add actions here

            R.id.bottom_sheet_item_reply -> {
                Toast.makeText(this, "Reply is clicked", Toast.LENGTH_SHORT).show()
            }

            R.id.bottom_sheet_teams_pm -> {

            }

            R.id.bottom_sheet_mark_re_open -> {

            }

            R.id.bottom_sheet_mark_close -> {

            }
        }
    }
}