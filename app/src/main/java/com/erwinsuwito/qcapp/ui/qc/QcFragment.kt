package com.erwinsuwito.qcapp.ui.qc

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erwinsuwito.qcapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class QcFragment : Fragment() {

    private lateinit var qcFragmentViewModel: QcFragmentViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        qcFragmentViewModel =
                ViewModelProvider(this).get(QcFragmentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_qc, container, false)

        var sharedPreferences = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        if (sharedPreferences!!.getString("usr_role", "Technical Assistant") == "Board Member")
        {
            val rootFab = root.findViewById<FloatingActionButton>(R.id.rootFab)
            rootFab.isEnabled = true
            rootFab.visibility = View.VISIBLE
        }

        return root
    }
}