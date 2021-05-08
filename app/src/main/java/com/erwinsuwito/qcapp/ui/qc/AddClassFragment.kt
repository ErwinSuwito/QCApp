package com.erwinsuwito.qcapp.ui.qc

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.erwinsuwito.qcapp.App
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.apis.FirestoreHelper
import com.erwinsuwito.qcapp.model.Classroom
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Timestamp

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddClassFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddClassFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val root =  inflater.inflate(R.layout.fragment_add_class, container, false)

        val className: TextInputEditText = root.findViewById(R.id.addClass_ClassName)
        val addClass_saveBtn = root.findViewById<Button>(R.id.addClass_saveBtn)
        val addClass_projectorAssetTag = root.findViewById<TextInputEditText>(R.id.addClass_projectorAssetTag)
        val addClass_projectorModel = root.findViewById<TextInputEditText>(R.id.addClass_projectorModel)
        val addClass_ipAddress = root.findViewById<TextInputEditText>(R.id.addClass_ipAddress)
        val addClass_highLampHour = root.findViewById<TextInputEditText>(R.id.addClass_highLampHour)
        val addClass_lowLampHour = root.findViewById<TextInputEditText>(R.id.addClass_lowLampHour)

        addClass_saveBtn.setOnClickListener {
            var classroom = Classroom(className.text.toString(), addClass_projectorAssetTag.text.toString(), addClass_projectorModel.text.toString(), addClass_ipAddress.text.toString(), addClass_highLampHour.text.toString().toInt(), addClass_lowLampHour.text.toString().toInt(), true,Timestamp.now())
            FirestoreHelper().addClass(classroom, { onSuccess() }, { onFailure() })
        }

        return root
    }

    fun onSuccess()
    {
        Toast.makeText(App.context, getString(R.string.class_added), Toast.LENGTH_SHORT).show()
        activity?.onBackPressed()
    }

    fun onFailure()
    {
        val builder = AlertDialog.Builder(App.context!!)
        builder.setTitle(R.string.unable_add_class)
        builder.setMessage(R.string.unable_add_class_message)
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
         * @return A new instance of fragment AddClassFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddClassFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}