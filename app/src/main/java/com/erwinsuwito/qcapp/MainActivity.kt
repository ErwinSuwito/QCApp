package com.erwinsuwito.qcapp

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.prrogress_dialog.*

class MainActivity : AppCompatActivity() {

    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_qc, R.id.fragment_qc_admin, R.id.noPermissionsFragment, R.id.navigation_more))
        // setupActionBarWithNavController(navController, appBarConfiguration)
        // navView.setupWithNavController(navController)
        NavigationUI.setupActionBarWithNavController(this,navController, appBarConfiguration)
        NavigationUI.setupWithNavController(navView, navController)
    }

    fun showProgressDialog(message: String? = null)
    {
        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.prrogress_dialog)
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        if (message != null)
        {
            progressDialog.progressText.text = message
        }
        progressDialog.show()
    }

    fun hideProgressDialog()
    {
        progressDialog.dismiss()
    }
}