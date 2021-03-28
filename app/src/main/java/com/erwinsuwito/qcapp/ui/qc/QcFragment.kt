package com.erwinsuwito.qcapp.ui.qc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.erwinsuwito.qcapp.R

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
        val textView: TextView = root.findViewById(R.id.text_notifications)
        qcFragmentViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}