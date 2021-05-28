package com.erwinsuwito.qcapp

import android.app.Dialog
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.erwinsuwito.qcapp.model.Issue
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {
    private lateinit var progressDialog: Dialog

    fun showSnackbar(message: String, duration: Int)
    {
        Snackbar.make(findViewById(android.R.id.content), message, duration)
                .show()
    }

    fun showSnackbar(message: String, duration:Int, actionText: String,  onClick: () -> Unit)
    {
        Snackbar.make(findViewById(android.R.id.content), message, duration)
                .setAction(actionText, View.OnClickListener { onClick() })
                .show()
    }

    fun showProgressDialog()
    {
        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.dialog_progress)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        progressDialog.show()
    }

    fun hideProgressDialog()
    {
        progressDialog.hide()
    }
}