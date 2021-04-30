package com.erwinsuwito.qcapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.AppState
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.adapter.TaskCardAdapter
import com.erwinsuwito.qcapp.model.MoreItem
import com.erwinsuwito.qcapp.model.Task
import kotlinx.android.synthetic.main.fragment_home.*
import java.time.LocalDateTime

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        var dummyTasksList = mutableListOf(
            Task("B-08-09", "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo" , "The projector can't project from any sources. Long HDMI, Short HDMI and EasyMP projection doesn't work.", "All projection doesn't work", LocalDateTime.now(), true),
            Task("D-08-09", "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo" , "The projector can't project from any sources. Long HDMI, Short HDMI and EasyMP projection doesn't work.", "All projection doesn't work", LocalDateTime.now(), true),
            Task("B-08-09", "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo" , "The projector can't project from any sources. Long HDMI, Short HDMI and EasyMP projection doesn't work.", "All projection doesn't work", LocalDateTime.now(), true),
            Task("D-08-09", "erwin.suwito@cloudmails.apu.edu.my", "Erwin Suwitoandojo" , "The projector can't project from any sources. Long HDMI, Short HDMI and EasyMP projection doesn't work.", "All projection doesn't work", LocalDateTime.now(), true)
        )

        val task_card_list = root.findViewById<RecyclerView>(R.id.task_card_list)
        task_card_list.adapter = TaskCardAdapter(root.context, dummyTasksList, { issue -> taskItemClicked(root.context, issue)})

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    fun taskItemClicked(context: Context, taskItem: Task)
    {
        Toast.makeText(context, "A task is clicked.", Toast.LENGTH_SHORT).show()
    }
}