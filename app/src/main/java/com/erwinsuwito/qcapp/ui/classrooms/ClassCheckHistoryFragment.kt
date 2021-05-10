package com.erwinsuwito.qcapp.ui.classrooms

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.App
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.adapter.ClassCheckHistoryAdapter
import com.erwinsuwito.qcapp.apis.FirestoreHelper
import com.erwinsuwito.qcapp.model.ClassCheck
import java.time.LocalDateTime

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClassCheckHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClassCheckHistoryFragment(var className: String) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var checkHistoryRecylerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_class_check_history, container, false)

        checkHistoryRecylerView = root.findViewById(R.id.checkHistoryRecylerView)

        FirestoreHelper().getCheckHistory(className, {onSuccess(it)}, {onFailure()})

        return root
    }

    fun onSuccess(checkHistory: MutableList<ClassCheck>) {
        checkHistoryRecylerView.adapter = ClassCheckHistoryAdapter(App.context!!, checkHistory, { itemClicked(it) })
        val dividerItemDecoration = DividerItemDecoration(
                checkHistoryRecylerView.context,
                1
        )
        checkHistoryRecylerView.addItemDecoration(dividerItemDecoration)
    }

    fun onFailure() {
        Toast.makeText(App.context!!, getString(R.string.unable_get_check_history), Toast.LENGTH_SHORT).show()
    }

    fun itemClicked(classCheck: ClassCheck)
    {
        val intent  = Intent(activity, ClassCheckDetail::class.java)
        intent.putExtra("selectedClassCheck", classCheck)
        activity?.startActivity(intent)
    }
}