package com.erwinsuwito.qcapp.ui.classrooms

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
import com.erwinsuwito.qcapp.model.Classroom
import com.erwinsuwito.qcapp.ui.issues.ClassIssueListFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.microsoft.fluentui.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_class_detail.*


class ClassDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_detail)

        var tab_viewpager = findViewById<ViewPager>(R.id.class_viewpager)
        var tab_tablayout = findViewById<TabLayout>(R.id.tabLayout)

        setupViewPager(tab_viewpager)
        tab_tablayout.setupWithViewPager(tab_viewpager)

        var selectedClass: String? = intent.extras?.getString("selectedClass")
        var classroom: Classroom? = intent.extras?.getParcelable<Classroom>("class")

        var topAppBar: MaterialToolbar = findViewById(R.id.topAppBar)

        var checkClassBtn: Button = findViewById(R.id.checkClassBtn)

        topAppBar.setNavigationOnClickListener { onBackPressed() }

        checkClassBtn.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.before_we_continue)
            builder.setMessage(R.string.qc_start_notice_message)
            builder.setPositiveButton(R.string.next) { dialog, which ->
                Toast.makeText(this, "Next button selected", Toast.LENGTH_SHORT).show()
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
    }

    fun snackbarActionClicked()
    {
        Toast.makeText(this, "Snackbar action clicked", Toast.LENGTH_SHORT).show()
    }

    private fun setupViewPager(viewpager: ViewPager) {
        var adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(ClassInfoFragment("D-08-09"), "Info")
        adapter.addFragment(ClassIssueListFragment("D-08-09"), "Issues")

        viewpager.setAdapter(adapter)
    }

    class ViewPagerAdapter : FragmentPagerAdapter {
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