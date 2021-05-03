package com.erwinsuwito.qcapp.ui.classrooms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.model.ClassCheck
import com.erwinsuwito.qcapp.model.Classroom
import kotlinx.android.synthetic.main.activity_class_check_detail.*
import org.w3c.dom.Text

class ClassCheckDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_check_detail)

        var selectedCheck: ClassCheck? = intent.extras?.getParcelable<ClassCheck>("selectedClassCheck")

        class_name_2.text = selectedCheck?.classId
        checked_on_textView.text = selectedCheck?.checkedOn.toString()
        checked_by_textView.text = selectedCheck?.checkedByName
        high_lamp_hour_checkDetail.text = selectedCheck?.highLampHour.toString()
        low_lamp_hour_checkDetail.text = selectedCheck?.lowHighLampHour.toString()
        alignment_textView.text = selectedCheck?.isAlignmentOk.toString()
        clarity_txtView.text = selectedCheck?.isClarityOk.toString()
        screen_textView.text = selectedCheck?.isScreenOk.toString()
        remote_textView.text = selectedCheck?.isRemoteOk.toString()
        sHDMI_textView.text = selectedCheck?.isShortHdmiOk.toString()
        lHDMI_textView.text = selectedCheck?.isLongHdmiOk.toString()
        easyMP_textView.text = selectedCheck?.isEasyMPOk.toString()
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
}