package com.erwinsuwito.qcapp.ui.classrooms

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.model.ClassCheck
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.activity_class_check_detail.*

class ClassCheckDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_check_detail)

        var selectedCheck: ClassCheck? = intent.extras?.getParcelable<ClassCheck>("selectedClassCheck")

        val topAppBar = findViewById<MaterialToolbar>(R.id.classCheck_Toolbar)
        val chatBtn = findViewById<Button>(R.id.button2)

        chatBtn.setOnClickListener {
            if (selectedCheck != null)
            {
                launchTeams(selectedCheck.checkedBy)
            }
            else
            {
                Toast.makeText(this, "Unable to start a Teams chat. Please try again later.", Toast.LENGTH_SHORT).show()
            }
        }

        class_name_2.text = selectedCheck?.classroomName
        checked_on_textView.text = selectedCheck?.checkedOn.toString()
        checked_by_textView.text = selectedCheck?.checkedByName
        high_lamp_hour_checkDetail.text = selectedCheck?.highLampHour.toString()
        low_lamp_hour_checkDetail.text = selectedCheck?.lowHighLampHour.toString()
        alignment_textView.text = selectedCheck?.isAlignmentOk.toString()
        clarity_txtView.text = selectedCheck?.isClarityOk.toString()
        screen_textView.text = selectedCheck?.isScreenOk.toString()
        remote_textView.text = selectedCheck?.isRemoteOk.toString()
        lHDMI_textView.text = selectedCheck?.isHdmiOk.toString()
        easyMP_textView.text = selectedCheck?.isEasyMpOk.toString()
        switch_ok_textView.text = selectedCheck?.isSwitchLabelOk.toString()
        guideline_textView.text = selectedCheck?.isGuidelineOk.toString()
        ap_textView.text = selectedCheck?.isApOn.toString()
        signal_strength_textView.text = selectedCheck?.apSignalStrength.toString()
        ping_textView.text = selectedCheck?.pingGoogle.toString()
        dlSpeed_textView.text = selectedCheck?.dlSpeed.toString()
        ulSpeed_textView.text = selectedCheck?.ulSpeed.toString()
        ip_textView.text = selectedCheck?.ipAddress
        powerExt_textView.text = selectedCheck?.isPowerExtAvailable.toString()
    }

    fun launchTeams(user: String)
    {
        val url = getString(R.string.teams_chat_link).replace("|users", user)
        val teamsIntent = getString(R.string.teams_chat_intent).replace("|users", user)

        try
        {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(teamsIntent)))
        }
        catch (e: Exception)
        {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }
}