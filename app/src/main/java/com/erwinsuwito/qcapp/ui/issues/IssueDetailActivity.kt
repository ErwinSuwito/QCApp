package com.erwinsuwito.qcapp.ui.issues

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.erwinsuwito.qcapp.BaseActivity
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.adapter.StepsAdapter
import com.erwinsuwito.qcapp.apis.FirestoreHelper
import com.erwinsuwito.qcapp.model.Issue
import com.erwinsuwito.qcapp.model.Steps
import com.erwinsuwito.qcapp.ui.tasks.AddReplyActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.Timestamp
import com.microsoft.fluentui.bottomsheet.BottomSheet
import com.microsoft.fluentui.bottomsheet.BottomSheetItem
import kotlinx.android.synthetic.main.activity_issue_detail.*
import org.w3c.dom.DOMStringList

class IssueDetailActivity : BaseActivity(), BottomSheetItem.OnClickListener {
    var selectedIssue: Issue? = null
    lateinit var upn: String
    lateinit var usrName: String
    lateinit var role: String
    lateinit var participant_upn: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_detail)

        selectedIssue = intent.extras?.getParcelable("issue")

        if (selectedIssue != null)
        {
            var sharedPreferences = this.getSharedPreferences("prefs", Context.MODE_PRIVATE)
            upn = sharedPreferences.getString("upn", "Technical Assistant")!!
            usrName = sharedPreferences.getString("usr_name", "User")!!
            role = sharedPreferences.getString("usr_role", "Techncial Assistant")!!

            showProgressDialog()
            added_by_avatar.name = selectedIssue!!.creatorName
            added_by_name.text = selectedIssue!!.creatorName
            issueDetail_location.text = selectedIssue!!.classroomName
            issueDetail_issueBody.text = selectedIssue!!.problem
            issueDetail_addedOn.text = selectedIssue!!.openedOn.toDate().toLocaleString()
            issueDetail_closedOn.text = selectedIssue!!.closedOn.toDate().toLocaleString()

            if (!selectedIssue!!.isOpen) {
                issueDetail_closedOnPanel.visibility = View.VISIBLE
            }

            FirestoreHelper().getIssueSteps(selectedIssue!!.issueId, {displaySteps(it)}, {onFailure()})

            issue_OverflowBtn.setOnClickListener {onOverflowButtonClicked()}
        }
        else
        {
            finish()
        }

        var topAppBar: MaterialToolbar = findViewById(R.id.issue_TopAppBar)
        topAppBar.setNavigationOnClickListener { onBackPressed() }

    }

    fun displaySteps(steps: MutableList<Steps>) {
        issueDetail_repliesList.adapter = StepsAdapter(this, steps, {onReplyClicked(it)})
        hideProgressDialog()
    }

    fun onOverflowButtonClicked() {
        var bottomSheetItems = arrayListOf(
                BottomSheetItem(
                        R.id.bottom_sheet_item_reply,
                        R.drawable.ic_chat,
                        getString(R.string.reply)
                )
        )

        if (role == "Board Member")
        {
            if (selectedIssue!!.isOpen)
            {
                bottomSheetItems.add(BottomSheetItem(R.id.bottom_sheet_mark_close, R.drawable.ic_complete, getString(R.string.close_issue)))
            }
            else
            {
                bottomSheetItems.add(BottomSheetItem(R.id.bottom_sheet_mark_re_open, R.drawable.ic_giftbox_open, getString(R.string.reopen_issue)))
            }
        }

        val bottomSheet = BottomSheet.newInstance(bottomSheetItems)
        bottomSheet.show(supportFragmentManager, null)
    }

    fun onReplyClicked(reply: Steps) {
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
        builder.setTitle(getString(R.string.unable_get_issue_detail))
        builder.setMessage(getString(R.string.unable_get_issue_detail_message))
        builder.setPositiveButton(R.string.okay) { dialog, which ->
            finish()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onBottomSheetItemClick(item: BottomSheetItem) {
        when (item.id) {
            R.id.bottom_sheet_item_reply -> {
                val intent = Intent(this, AddReplyActivity::class.java).putExtra("issue", selectedIssue).putExtra("itemType", 2)
                this.startActivity(intent)
            }

            R.id.bottom_sheet_teams_pm_starter -> {
                val url = getString(R.string.teams_chat_link).replace("|users", selectedIssue!!.creator)
                val teamsIntent = getString(R.string.teams_chat_intent).replace("|users", selectedIssue!!.creatorName)

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
                selectedIssue!!.isOpen = true
                updateIssue()
            }

            R.id.bottom_sheet_mark_close -> {
                selectedIssue!!.isOpen = false
                selectedIssue!!.closedOn = Timestamp.now()
                selectedIssue!!.closedBy = upn
                selectedIssue!!.closedByName = usrName
                updateIssue()
            }
        }
    }

    fun updateIssue() {
        FirestoreHelper().addIssue(selectedIssue!!, {updateSuccess()}, {updateFailed()})
    }

    fun updateSuccess() {
        Toast.makeText(this, getString(R.string.issue_updated), Toast.LENGTH_LONG).show()
    }

    fun updateFailed() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.unable_update_issue)
        builder.setMessage(getString(R.string.unable_update_issue_message))
        builder.setPositiveButton(R.string.okay) { dialog, which ->
            finish()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}