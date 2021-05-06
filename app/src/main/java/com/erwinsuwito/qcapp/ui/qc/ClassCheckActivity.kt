package com.erwinsuwito.qcapp.ui.qc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erwinsuwito.qcapp.BaseActivity
import com.erwinsuwito.qcapp.R
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.activity_class_check.*

class ClassCheckActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_check)

        var topAppBar: MaterialToolbar = findViewById(R.id.check_topAppBar)
        topAppBar.setNavigationOnClickListener { onBackPressed() }
    }
}