package com.erwinsuwito.qcapp.ui.qc

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.viewpager.widget.ViewPager
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.model.ClassCheck
import com.erwinsuwito.qcapp.model.Classroom
import com.erwinsuwito.qcapp.ui.classrooms.ClassCheckHistoryFragment
import com.erwinsuwito.qcapp.ui.classrooms.ClassInfoFragment
import com.erwinsuwito.qcapp.ui.issues.ClassIssueListFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.microsoft.fluentui.util.activity
import kotlinx.android.synthetic.main.qcadmin_fabs_layout.*
import java.time.LocalDateTime

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

        var tab_viewpager = root.findViewById<ViewPager>(R.id.qc_viewpager)
        var tab_tablayout = root.findViewById<TabLayout>(R.id.qc_tabLayout)

        tab_tablayout.setupWithViewPager(tab_viewpager)
        setupViewPager(tab_viewpager)

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

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter: ViewPagerAdapter = ViewPagerAdapter(childFragmentManager)

        adapter.addFragment(ClassIssueListFragment("D-08-09"), "Issues")

        viewpager.setAdapter(adapter)
    }

    private class ViewPagerAdapter : FragmentPagerAdapter {
        private final var fragmentList1: ArrayList<Fragment> = ArrayList()
        private final var fragmentTitleList1: ArrayList<String> = ArrayList()

        public constructor(supportFragmentManager: FragmentManager)
                : super(supportFragmentManager)

        override fun getItem(position: Int): Fragment {
            return fragmentList1.get(position)
        }

        @Nullable
        override fun getPageTitle(position: Int): CharSequence {
            return fragmentTitleList1.get(position)
        }

        override fun getCount(): Int {
            return fragmentList1.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList1.add(fragment)
            fragmentTitleList1.add(title)
        }

    }
}