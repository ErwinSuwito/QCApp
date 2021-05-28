package com.erwinsuwito.qcapp.ui.qc

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.erwinsuwito.qcapp.App
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.model.Issue
import com.google.android.material.textfield.TextInputEditText
import com.erwinsuwito.qcapp.apis.FirebaseIDGenerator
import com.erwinsuwito.qcapp.apis.FirestoreHelper
import com.google.firebase.Timestamp

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddIssueFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddIssueFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var issue_classNameTextBox: TextInputEditText
    lateinit var issue_problemTextBox: TextInputEditText
    lateinit var issue_saveBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_issue, container, false)

        issue_classNameTextBox = root.findViewById(R.id.issue_classNameTextBox)
        issue_problemTextBox = root.findViewById(R.id.issue_problemTextBox)
        issue_saveBtn = root.findViewById(R.id.issue_saveBtn)
        val sharedPreferences = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val usrName = sharedPreferences!!.getString("usr_name", "User")
        val upn = sharedPreferences!!.getString("upn", "someone@cloudmails.apu.edu.my")

        issue_saveBtn.setOnClickListener{
            clearErorrs()
            val isValidationSuccessful = validateDetails()
            if (isValidationSuccessful)
            {
                var issue = Issue(FirebaseIDGenerator.generateId(), issue_classNameTextBox.text.toString(), upn!!, usrName!!, issue_problemTextBox.text.toString(), Timestamp.now(), Timestamp.now(), "", "", true)
                FirestoreHelper().addIssue(issue, {onSuccess()}, {onFailure()})
            }
        }

        return root
    }

    fun validateDetails(): Boolean
    {
        return when {
            TextUtils.isEmpty(issue_classNameTextBox.text.toString().trim { it <= ' ' }) -> {
                issue_classNameTextBox.error = getString(R.string.require_data)
                false
            }
            TextUtils.isEmpty(issue_problemTextBox.text.toString().trim { it <= ' ' }) -> {
                issue_problemTextBox.error = getString(R.string.require_data)
                false
            }
            else -> true
        }
    }

    fun clearErorrs()
    {
        issue_classNameTextBox.error = null
        issue_problemTextBox.error = null
    }

    fun onSuccess()
    {
        Toast.makeText(App.context, getString(R.string.issue_added), Toast.LENGTH_SHORT).show()
        activity?.onBackPressed()
    }

    fun onFailure()
    {
        val builder = AlertDialog.Builder(App.context!!)
        builder.setTitle(R.string.unable_add_issue)
        builder.setMessage(R.string.unable_add_issue_message)
        builder.setPositiveButton(R.string.okay) { dialog, which ->

        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddIssueFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddIssueFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}