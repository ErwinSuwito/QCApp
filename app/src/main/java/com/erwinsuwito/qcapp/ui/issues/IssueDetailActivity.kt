package com.erwinsuwito.qcapp.ui.issues

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.erwinsuwito.qcapp.R
import com.google.android.material.appbar.MaterialToolbar
import com.microsoft.fluentui.bottomsheet.BottomSheet
import com.microsoft.fluentui.bottomsheet.BottomSheetItem

class IssueDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_detail)

        var topAppBar: MaterialToolbar = findViewById(R.id.issue_TopAppBar)
        topAppBar.setNavigationOnClickListener { onBackPressed() }

        topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.task_BottomSheet -> {
                    Toast.makeText(this, "Action clicked", Toast.LENGTH_SHORT).show()

                    // TO-DO: Add code to modify the following list
                    val bottomSheet = BottomSheet.newInstance(
                        arrayListOf(
                            BottomSheetItem(
                                R.id.bottom_sheet_item_reply,
                                R.drawable.ic_chat,
                                "Reply"
                            ),
                            BottomSheetItem(
                                R.id.bottom_sheet_mark_close,
                                R.drawable.ic_complete,
                                "Close issue"
                            ),
                            BottomSheetItem(
                                R.id.bottom_sheet_mark_re_open,
                                R.drawable.ic_giftbox_open,
                                "Re-open issue"
                            ),
                            BottomSheetItem(
                                R.id.bottom_sheet_teams_pm,
                                R.drawable.ic_teams,
                                getString(R.string.chat_teams)
                            ),
                            BottomSheetItem(
                                R.id.bottom_sheet_teams_group,
                                R.drawable.ic_group,
                                "Group chat on Teams"
                            )
                        )
                    )
                    bottomSheet.show(supportFragmentManager, null)
                    true
                }

                else -> false
            }
        }
    }
}