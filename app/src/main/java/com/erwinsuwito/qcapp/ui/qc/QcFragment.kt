package com.erwinsuwito.qcapp.ui.qc

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.erwinsuwito.qcapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_qc_admin.*
import kotlinx.android.synthetic.main.qcadmin_fabs_layout.*

class QcFragment : Fragment(), View.OnClickListener {


    //TO-DO: Complete the codes to show or hide FABs
    private lateinit var qcFragmentViewModel: QcFragmentViewModel
    var isOpen = false
    private var fab_open: Animation? = null
    private var fab_close: Animation? = null
    private var fab_clock: Animation? = null
    private var fab_anticlock: Animation? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        qcFragmentViewModel =
                ViewModelProvider(this).get(QcFragmentViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_qc, container, false)

        var sharedPreferences = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        fab_open = AnimationUtils.loadAnimation(this.context, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this.context, R.anim.fab_close)
        fab_clock = AnimationUtils.loadAnimation(this.context, R.anim.fab_rotate_clock)
        fab_anticlock = AnimationUtils.loadAnimation(this.context, R.anim.fab_rotate_anticlock)

        val rootFab = root.findViewById<FloatingActionButton>(R.id.rootFab)
        val addTasksFab = root.findViewById<FloatingActionButton>(R.id.addTasksFab)
        val addClassFab = root.findViewById<FloatingActionButton>(R.id.addClassFab)

        if (sharedPreferences!!.getString("usr_role", "Technical Assistant") == "Board Member")
        {
            rootFab.isEnabled = true
            rootFab.visibility = View.VISIBLE
        }

        rootFab.setOnClickListener(this)
        addTasksFab.setOnClickListener(this)
        addClassFab.setOnClickListener(this)

        return root
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
                        textview_tasks.visibility = View.INVISIBLE
                        addClassFab.startAnimation(fab_close)
                        addTasksFab.startAnimation(fab_close)
                        addClassFab.isClickable = false
                        addTasksFab.isClickable = false
                        rootFab.startAnimation(fab_anticlock)
                        isOpen = false
                    }
                    else
                    {
                        textview_class.visibility = View.VISIBLE
                        textview_tasks.visibility = View.VISIBLE
                        addClassFab.startAnimation(fab_open)
                        addTasksFab.startAnimation(fab_open)
                        addClassFab.isClickable = true
                        addTasksFab.isClickable = true
                        rootFab.startAnimation(fab_clock)
                        isOpen = true
                    }
                }
            }
        }
    }
}