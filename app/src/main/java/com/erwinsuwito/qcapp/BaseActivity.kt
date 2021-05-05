package com.erwinsuwito.qcapp

import android.app.Dialog
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.erwinsuwito.qcapp.model.Issue
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {
    private lateinit var progressDialog: Dialog

    fun showSnackbar(message: String)
    {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
                .show()
    }

    fun showSnackbar(message: String, actionText: String,  onClick: () -> Unit)
    {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
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
}