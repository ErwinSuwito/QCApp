package com.erwinsuwito.qcapp.ui.qc

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.App
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.adapter.ClassroomAdapter
import com.erwinsuwito.qcapp.apis.FirestoreHelper
import com.erwinsuwito.qcapp.model.Classroom
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_class_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClassListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClassListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    /*
    var class_progressBar: ProgressBar? = null
    var no_classes_textView: TextView? = null
    var classList_RecyclerView: RecyclerView? = null
    var addClassFab2: FloatingActionButton? = null
     */

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
        return inflater.inflate(R.layout.fragment_class_list, container, false)
    }

    fun onSuccess(classList: MutableList<Classroom>)  {
        class_progressBar.visibility = View.GONE
        if (classList.count() < 1)
        {
            no_classes_textView.visibility = View.VISIBLE
        }
        else
        {
            classList_RecyclerView.visibility = View.VISIBLE
            classList_RecyclerView.adapter = ClassroomAdapter(App.context!!, classList, {itemOnClick(it)})
        }
    }

    fun onFail() {
        no_classes_textView.visibility = View.VISIBLE
        no_classes_textView.text = "Unable to get classes"
        class_progressBar.visibility = View.GONE
    }

    fun itemOnClick(classroom: Classroom) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val sharedPreferences = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)

        if (sharedPreferences!!.getString("usr_role", "Technical Assistant") != "Board Member")
        {
            addClassFab2.visibility = View.GONE
        }

        addClassFab2.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_qc_to_addClassFragment2)
        }

        FirestoreHelper().getClassList({onSuccess(it)}, {onFail()})
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClassListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ClassListFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}