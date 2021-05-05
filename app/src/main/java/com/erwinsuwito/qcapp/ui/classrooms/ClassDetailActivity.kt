package com.erwinsuwito.qcapp.ui.classrooms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.erwinsuwito.qcapp.BaseActivity
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.model.ClassCheck
import com.erwinsuwito.qcapp.model.Classroom
import com.erwinsuwito.qcapp.ui.issues.ClassIssueListFragment
import com.erwinsuwito.qcapp.ui.qc.ClassCheckActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.microsoft.fluentui.snackbar.Snackbar
import com.microsoft.fluentui.util.activity
import kotlinx.android.synthetic.main.activity_class_detail.*
import java.time.LocalDateTime


class ClassDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_detail)

        var tab_viewpager = findViewById<ViewPager>(R.id.class_viewpager)
        var tab_tablayout = findViewById<TabLayout>(R.id.tabLayout)

        tab_tablayout.setupWithViewPager(tab_viewpager)

        var classroom: Classroom? = intent.extras?.getParcelable<Classroom>("class")
        setupViewPager(tab_viewpager, classroom)

        var topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)

        var checkClassBtn: Button = findViewById(R.id.checkClassBtn)

        topAppBar.setNavigationOnClickListener { onBackPressed() }

        checkClassBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.before_we_continue)
            builder.setMessage(R.string.qc_start_notice_message)
            builder.setPositiveButton(R.string.next) { dialog, which ->
                val intent = Intent(activity, ClassCheckActivity::class.java)
                activity?.startActivity(intent)
            }
            builder.setNegativeButton(R.string.cancel) { dialog, which ->
                Toast.makeText(this, "Cancel button selected", Toast.LENGTH_SHORT).show()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }

        class_name.text = classroom?.className
        if (classroom?.isChecked == true)
        {
            class_status.text = getString(R.string.no_problems_found)
            checkClassBtn.isEnabled = false
            checkClassBtn.text = getString(R.string.class_checked)
        }
        else
        {
            class_status.text = getString(R.string.problems_found)
        }

        showProgressDialog()
    }

    private fun setupViewPager(viewpager: ViewPager, classroom: Classroom?) {
        var adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        if (classroom != null) {
            adapter.addFragment(ClassInfoFragment(classroom.projectorId, classroom.ipAddress, classroom.highLampHour, classroom.lowLampHour, classroom.lastChecked.toString()), "Info")
        }
        adapter.addFragment(ClassIssueListFragment("D-08-09"), "Issues")

        var sharedPreferences = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        if (sharedPreferences!!.getString("usr_role", "Technical Assistant") == "Board Member")
        {
            var dummyCheckHistory = listOf<ClassCheck>(
                    ClassCheck("", "TP045000@mail.apu.edu.my", "ERWIN SUWITOANDOJO", "B-06-05", 1200, 200, true, true, true, true, true, true, true, true, true, true, 5, 10 , 120.1, 10.1, "192.168.1.1", true, LocalDateTime.now(), false),
                    ClassCheck("", "TP045000@mail.apu.edu.my", "ERWIN SUWITOANDOJO", "B-06-05", 1200, 200, true, true, true, true, true, true, true, true, true, true, 5, 10 , 120.1, 10.1, "192.168.1.1", true, LocalDateTime.now(), true),
                    ClassCheck("", "TP045000@mail.apu.edu.my", "ERWIN SUWITOANDOJO", "B-06-05", 1200, 200, true, true, true, true, true, true, true, true, true, true, 5, 10 , 120.1, 10.1, "192.168.1.1", true, LocalDateTime.now(), false),
                    ClassCheck("", "TP045000@mail.apu.edu.my", "ERWIN SUWITOANDOJO", "B-06-05", 1200, 200, true, true, true, true, true, true, true, true, true, true, 5, 10 , 120.1, 10.1, "192.168.1.1", true, LocalDateTime.now(), true),
                    ClassCheck("", "TP045000@mail.apu.edu.my", "ERWIN SUWITOANDOJO", "B-06-05", 1200, 200, true, true, true, true, true, true, true, true, true, true, 5, 10 , 120.1, 10.1, "192.168.1.1", true, LocalDateTime.now(), true)
            )

            adapter.addFragment(ClassCheckHistoryFragment(dummyCheckHistory), "Check history")
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