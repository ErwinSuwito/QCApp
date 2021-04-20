package com.erwinsuwito.qcapp.ui.qcadmin

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.erwinsuwito.qcapp.R

class QcAdminFragment : Fragment() {

    companion object {
        fun newInstance() = QcAdminFragment()
    }

    private lateinit var viewModel: QcAdminViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_qc_admin, container, false)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(QcAdminViewModel::class.java)
        // TODO: Use the ViewModel

        val sharedPreferences = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        val role = sharedPreferences?.getString("usr_role", "")

        if (!role.equals("Board Members")) {
            /// Navigation.findNavController(root).navigate(R.id.action_fragment_qc_admin_to_qcAdminNotAllowed)
            requireView().findNavController().navigate(R.id.action_fragment_qc_admin_to_noPermissionsFragment)
        }
    }

}