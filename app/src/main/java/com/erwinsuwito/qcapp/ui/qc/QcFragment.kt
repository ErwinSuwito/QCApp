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
import java.time.LocalDateTime

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

        /*
        TO-DO: Move this code to the other fragments
        var sharedPreferences = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        if (sharedPreferences!!.getString("usr_role", "Technical Assistant") == "Board Member")
        {
            rootFab.isEnabled = true
            rootFab.visibility = View.VISIBLE
        }
         */

        var tab_viewpager = root.findViewById<ViewPager>(R.id.qc_viewpager)
        var tab_tablayout = root.findViewById<TabLayout>(R.id.qc_tabLayout)

        tab_tablayout.setupWithViewPager(tab_viewpager)
        setupViewPager(tab_viewpager)

        return root
    }


    private fun setupViewPager(viewpager: ViewPager) {
        var adapter: ViewPagerAdapter = ViewPagerAdapter(childFragmentManager)

        adapter.addFragment(ClassListFragment(), "Classes")
        adapter.addFragment(IssueListFragment(), "Issues")
        adapter.addFragment(TasksListFragment(), "Tasks")

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