package com.erwinsuwito.qcapp.ui.qcadmin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.erwinsuwito.qcapp.R
import kotlinx.android.synthetic.main.fragment_qc_admin.*
import kotlinx.android.synthetic.main.qcadmin_fabs_layout.*


class QcAdminFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = QcAdminFragment()
    }

    private lateinit var viewModel: QcAdminViewModel
    var isOpen = false
    private var fab_open: Animation? = null
    private var fab_close:Animation? = null
    private var fab_clock:Animation? = null
    private var fab_anticlock:Animation? = null

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

        if (!(role.equals(getString(R.string.board)))) {
            /// Navigation.findNavController(root).navigate(R.id.action_fragment_qc_admin_to_qcAdminNotAllowed)
            requireView().findNavController().navigate(R.id.action_fragment_qc_admin_to_noPermissionsFragment)
        }

        fab_open = AnimationUtils.loadAnimation(this.context, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this.context, R.anim.fab_close)
        fab_clock = AnimationUtils.loadAnimation(this.context, R.anim.fab_rotate_clock)
        fab_anticlock = AnimationUtils.loadAnimation(this.context, R.anim.fab_rotate_anticlock)

        rootFab.setOnClickListener(this)
        addTasksFab.setOnClickListener(this)
        addProjectorFab.setOnClickListener(this)
        addClassFab.setOnClickListener(this)
        classCard.setOnClickListener(this)
        projectorCard.setOnClickListener(this)
        issueCard.setOnClickListener(this)
    }

    override fun onClick(v: View?)
    {
        if (v != null)
        {
            when (v.id)
            {
                R.id.rootFab -> {
                    if (isOpen)
                    {
                        textview_class.visibility = View.INVISIBLE
                        textview_projector.visibility = View.INVISIBLE
                        textview_tasks.visibility = View.INVISIBLE
                        addClassFab.startAnimation(fab_close)
                        addProjectorFab.startAnimation(fab_close)
                        addTasksFab.startAnimation(fab_close)
                        addClassFab.isClickable = false
                        addProjectorFab.isClickable = false
                        addTasksFab.isClickable = false
                        rootFab.startAnimation(fab_anticlock)
                        isOpen = false
                    }
                    else
                    {
                        textview_class.visibility = View.VISIBLE
                        textview_projector.visibility = View.VISIBLE
                        textview_tasks.visibility = View.VISIBLE
                        addClassFab.startAnimation(fab_open)
                        addProjectorFab.startAnimation(fab_open)
                        addTasksFab.startAnimation(fab_open)
                        addClassFab.isClickable = true
                        addProjectorFab.isClickable = true
                        addTasksFab.isClickable = true
                        rootFab.startAnimation(fab_clock)
                        isOpen = true
                    }
                }

                R.id.addTasksFab -> {
                    Toast.makeText(this.context, "Add Task or Issue FAB clicked", Toast.LENGTH_SHORT).show()
                }

                R.id.addProjectorFab -> {
                    requireView().findNavController().navigate(R.id.action_fragment_qc_admin_to_addProjecforFragment)
                }

                R.id.addClassFab -> {
                    requireView().findNavController().navigate(R.id.action_fragment_qc_admin_to_addClassFragment)
                }

                R.id.issueCard -> {
                    Toast.makeText(this.context, "Add Task or Issue card clicked", Toast.LENGTH_SHORT).show()
                }

                R.id.projectorCard -> {
                    requireView().findNavController().navigate(R.id.action_fragment_qc_admin_to_projectorListFragment)
                }

                R.id.classCard -> {
                    Toast.makeText(this.context, "Add Class card clicked", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}