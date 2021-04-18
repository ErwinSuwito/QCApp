package com.erwinsuwito.qcapp.ui.qcadmin

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.erwinsuwito.qcapp.R

class QcAdminFragment : Fragment() {

    companion object {
        fun newInstance() = QcAdminFragment()
    }

    private lateinit var viewModel: QcAdminViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.qc_admin_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QcAdminViewModel::class.java)
        // TODO: Use the ViewModel
    }

}