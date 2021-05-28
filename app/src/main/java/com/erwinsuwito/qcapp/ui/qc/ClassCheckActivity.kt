package com.erwinsuwito.qcapp.ui.qc

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
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
    lateinit var classNameEditText: TextInputEditText
    lateinit var highLampHourEditText: TextInputEditText
    lateinit var lowLampHourEditText: TextInputEditText
    lateinit var signalStrengthEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_check)

        classroom = this.intent.extras!!.getParcelable<Classroom>("classroom")!!
        classNameEditText = findViewById(R.id.check_classNameTextBox)
        classNameEditText.setText(classroom!!.classroomName)

        val topAppBar: MaterialToolbar = findViewById(R.id.check_topAppBar)
        topAppBar.setNavigationOnClickListener { onBackPressed() }

        classCheck_AddBtn.setOnClickListener {
            if (validateDetails())
            {
                saveQc()
            }
        }
    }

    fun validateDetails(): Boolean {
        return when {
            TextUtils.isEmpty(classNameEditText.text.toString().trim { it <= ' ' }) -> {
                classNameEditText.error = getString(R.string.require_data)
                false
            }
            TextUtils.isEmpty(highLampHourEditText.text.toString().trim { it <= ' ' }) -> {
                highLampHourEditText.error = getString(R.string.require_data)
                false
            }
            TextUtils.isEmpty(lowLampHourEditText.text.toString().trim { it <= ' ' }) -> {
                lowLampHourEditText.error = getString(R.string.require_data)
                false
            }
            TextUtils.isEmpty(signalStrengthEditText.text.toString().trim { it <= ' ' }) -> {
                signalStrengthEditText.error = getString(R.string.require_data)
                false
            }
            else -> true
        }
    }

    fun saveQc() {
        showProgressDialog()

        var signalStrength = 0
        var pingGoogle = 0
        var ulSpeed = 0.0
        var dlSpeed = 0.0
        var highLampHour = 0
        var lowLampHour = 0

        try
        {
            signalStrength = findViewById<TextInputEditText>(R.id.check_signalStrengthTextBox).text.toString().toInt()
            pingGoogle = findViewById<TextInputEditText>(R.id.check_pingGoogleTextBox).text.toString().toInt()
            ulSpeed = findViewById<TextInputEditText>(R.id.check_ulSpeedTextBox).text.toString().toDouble()
            dlSpeed = findViewById<TextInputEditText>(R.id.check_dlSpeedTextBox).text.toString().toDouble()
            highLampHour = findViewById<TextInputEditText>(R.id.check_highLampHourTextBox).text.toString().toInt()
            lowLampHour = findViewById<TextInputEditText>(R.id.check_lowLampHourTextBox).text.toString().toInt()
        }
        catch (exception: Exception)
        { }

        var usrName = this.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("usr_name", "User")
        var upn = this.getSharedPreferences("prefs", Context.MODE_PRIVATE).getString("upn", "someone@cloudmails.apu.edu.my")
        var className: String = findViewById<TextInputEditText>(R.id.check_classNameTextBox).text.toString()
        var ipAddress: String = findViewById<TextInputEditText>(R.id.check_ipAddressTextBox).text.toString()
        var alignment: Boolean = findViewById<CheckBox>(R.id.classCheck_alignment).isChecked
        var clarity: Boolean = findViewById<CheckBox>(R.id.classCheck_clarity).isChecked
        var screen: Boolean = findViewById<CheckBox>(R.id.classCheck_screen).isChecked
        var remote: Boolean = findViewById<CheckBox>(R.id.classCheck_Remote).isChecked
        var hdmi: Boolean = findViewById<CheckBox>(R.id.classCheck_Hdmi).isChecked
        var easyMp: Boolean = findViewById<CheckBox>(R.id.classCheck_EasyMp).isChecked
        var switch: Boolean = findViewById<CheckBox>(R.id.classCheck_Switch).isChecked
        var guideline: Boolean = findViewById<CheckBox>(R.id.classCheck_Guideline).isChecked
        var powerExt: Boolean = findViewById<CheckBox>(R.id.classCheck_powerExt).isChecked
        var ap: Boolean = signalStrength > 0
        var isEverythingOk: Boolean = alignment && clarity && screen && remote && hdmi && easyMp && switch && guideline && powerExt && ap

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
        Toast.makeText(this, getString(R.string.successfully_saved), Toast.LENGTH_LONG).show()
        finish()
    }

    private fun onUpdateClassFailure() {
        hideProgressDialog()
        val builder = AlertDialog.Builder(App.context!!)
        builder.setTitle(getString(R.string.completed_failure))
        builder.setMessage(getString(R.string.unable_update_class_qc))
        builder.setPositiveButton(R.string.okay) { dialog, which ->
            finish()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun onCheckSaveFailure() {
        hideProgressDialog()
        val builder = AlertDialog.Builder(App.context!!)
        builder.setTitle(getString(R.string.unable_save_qc))
        builder.setMessage(getString(R.string.unable_save_qc_message))
        builder.setPositiveButton(R.string.okay) { dialog, which ->

        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}