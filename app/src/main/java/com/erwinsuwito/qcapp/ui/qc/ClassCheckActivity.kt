package com.erwinsuwito.qcapp.ui.qc

import android.content.Context
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.erwinsuwito.qcapp.App
import com.erwinsuwito.qcapp.BaseActivity
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.apis.FirebaseIDGenerator
import com.erwinsuwito.qcapp.apis.FirestoreHelper
import com.erwinsuwito.qcapp.model.ClassCheck
import com.erwinsuwito.qcapp.model.Classroom
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.activity_class_check.*

class ClassCheckActivity : BaseActivity() {
    lateinit var classroom: Classroom
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_check)

        val classroom = this.intent.extras!!.getParcelable<Classroom>("classroom")
        findViewById<TextInputEditText>(R.id.check_classNameTextBox).setText(classroom!!.classroomName)

        val topAppBar: MaterialToolbar = findViewById(R.id.check_topAppBar)
        topAppBar.setNavigationOnClickListener { onBackPressed() }

        classCheck_AddBtn.setOnClickListener { saveQc() }
    }

    fun saveQc() {
        showProgressDialog()
        val usrName = this.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("usr_name", "User")
        val upn = this.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("upn", "someone@cloudmails.apu.edu.my")
        val className: String = findViewById<TextInputEditText>(R.id.check_classNameTextBox).text.toString()
        val highLampHour: Int = findViewById<TextInputEditText>(R.id.check_highLampHourTextBox).text.toString().toInt()
        val lowLampHour: Int = findViewById<TextInputEditText>(R.id.check_lowLampHourTextBox).text.toString().toInt()
        val ipAddress: String = findViewById<TextInputEditText>(R.id.check_ipAddressTextBox).text.toString()
        val signalStrength: Int = findViewById<TextInputEditText>(R.id.check_signalStrengthTextBox).text.toString().toInt()
        val pingGoogle: Int = findViewById<TextInputEditText>(R.id.check_pingGoogleTextBox).text.toString().toInt()
        val ulSpeed: Double = findViewById<TextInputEditText>(R.id.check_ulSpeedTextBox).text.toString().toDouble()
        val dlSpeed: Double = findViewById<TextInputEditText>(R.id.check_dlSpeedTextBox).text.toString().toDouble()
        val alignment: Boolean = findViewById<CheckBox>(R.id.classCheck_alignment).isChecked
        val clarity: Boolean = findViewById<CheckBox>(R.id.classCheck_clarity).isChecked
        val screen: Boolean = findViewById<CheckBox>(R.id.classCheck_screen).isChecked
        val remote: Boolean = findViewById<CheckBox>(R.id.classCheck_Remote).isChecked
        val hdmi: Boolean = findViewById<CheckBox>(R.id.classCheck_Hdmi).isChecked
        val easyMp: Boolean = findViewById<CheckBox>(R.id.classCheck_EasyMp).isChecked
        val switch: Boolean = findViewById<CheckBox>(R.id.classCheck_Switch).isChecked
        val guideline: Boolean = findViewById<CheckBox>(R.id.classCheck_Guideline).isChecked
        val powerExt: Boolean = findViewById<CheckBox>(R.id.classCheck_powerExt).isChecked
        val ap: Boolean = signalStrength > 0
        val isEverythingOk: Boolean = alignment && clarity && screen && remote && hdmi && easyMp && switch && guideline && powerExt && ap

        val qcCheck = ClassCheck(FirebaseIDGenerator.generateId(), upn!!, usrName!!, className, highLampHour, lowLampHour, alignment, clarity, screen, remote, hdmi, easyMp, switch, guideline, ap, signalStrength, pingGoogle, dlSpeed,ulSpeed, ipAddress, powerExt, isEverythingOk)
        FirestoreHelper().addChecks(qcCheck, {updateClassroomData()}, {onCheckSaveFailure()})

        classroom.lowLampHour = lowLampHour
        classroom.highLampHour = highLampHour
        classroom.isEverythingOk = isEverythingOk
    }

    private fun updateClassroomData() {
        classroom.lastChecked = Timestamp.now()
        FirestoreHelper().addClass(classroom, {completeCheck()}, {onUpdateClassFailure()})
    }

    private fun completeCheck() {
        hideProgressDialog()
        Toast.makeText(this, "Successfully saved", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun onUpdateClassFailure() {
        hideProgressDialog()
        val builder = AlertDialog.Builder(App.context!!)
        builder.setTitle("Completed with failure")
        builder.setMessage("Your QC check has been successfully. However, the class data wasn't updated successfully. You may see expired data for the class")
        builder.setPositiveButton(R.string.okay) { dialog, which ->
            finish()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun onCheckSaveFailure() {
        hideProgressDialog()
        val builder = AlertDialog.Builder(App.context!!)
        builder.setTitle("Unable to save QC check")
        builder.setMessage("We're unable to save your QC check. Please try again later.")
        builder.setPositiveButton(R.string.okay) { dialog, which ->

        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}