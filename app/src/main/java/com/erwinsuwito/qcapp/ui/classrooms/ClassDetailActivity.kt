package com.erwinsuwito.qcapp.ui.classrooms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import android.widget.Button
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.erwinsuwito.qcapp.BaseActivity
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.model.ClassCheck
import com.erwinsuwito.qcapp.model.Classroom
import com.erwinsuwito.qcapp.model.Issue
import com.erwinsuwito.qcapp.ui.issues.ClassIssueListFragment
import com.erwinsuwito.qcapp.ui.qc.ClassCheckActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.microsoft.fluentui.util.activity
import kotlinx.android.synthetic.main.activity_class_detail.*


class ClassDetailActivity : BaseActivity() {
    var tab_viewpager: ViewPager? = null
    var tab_tablayout: TabLayout? = null
    var classroom: Classroom? = null
    var issueList: MutableList<Issue>? = null
    var checkList: MutableList<ClassCheck>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_detail)

        tab_viewpager = findViewById(R.id.class_viewpager)
        tab_tablayout = findViewById(R.id.tabLayout)

        tab_tablayout!!.setupWithViewPager(tab_viewpager)

        classroom = intent.extras?.getParcelable<Classroom>("class")

        var topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)

        var checkClassBtn: Button = findViewById(R.id.checkClassBtn)

        topAppBar.setNavigationOnClickListener { onBackPressed() }

        checkClassBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.before_we_continue)
            builder.setMessage(R.string.qc_start_notice_message)
            builder.setPositiveButton(R.string.next) { dialog, which ->
                val intent = Intent(activity, ClassCheckActivity::class.java).putExtra("classname", classroom!!.classroomName)
                activity?.startActivity(intent)
            }
            builder.setNegativeButton(R.string.cancel) { dialog, which ->
                Toast.makeText(this, "Cancel button selected", Toast.LENGTH_SHORT).show()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        class_name.text = classroom?.classroomName
        if (DateUtils.isToday(classroom!!.lastChecked.toDate().toInstant().toEpochMilli()))
        {
            checkClassBtn.isEnabled = false
            checkClassBtn.text = getString(R.string.class_checked)
        }

        setupViewPager(tab_viewpager!!)
    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        var isSuccess = false

        adapter.addFragment(ClassInfoFragment(classroom!!), "Info")
        adapter.addFragment(ClassIssueListFragment(classroom!!.classroomName), "Issues")


        var sharedPreferences = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        if (sharedPreferences!!.getString("usr_role", "Technical Assistant") == "Board Member")
        {
            adapter.addFragment(ClassCheckHistoryFragment(classroom!!.classroomName), "Check History")
        }

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