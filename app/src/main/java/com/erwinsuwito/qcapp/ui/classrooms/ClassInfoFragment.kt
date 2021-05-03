package com.erwinsuwito.qcapp.ui.classrooms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.erwinsuwito.qcapp.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ClassInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ClassInfoFragment(var projectorModel: String, var projectorIpAddress: String,
var highLampHour: Int, var lowLampHour: Int, var lastChecked: String) : Fragment() {
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
        val root = inflater.inflate(R.layout.fragment_class_info, container, false)

        val projModelTxt = root.findViewById<TextView>(R.id.projectorModelTextBox)
        val projIpAddress = root.findViewById<TextView>(R.id.projector_ip_address_textView)
        val highLampHourTxt = root.findViewById<TextView>(R.id.high_lamp_hour_textView)
        val lowLampHourText = root.findViewById<TextView>(R.id.low_lamp_hour_textView)
        val lastCheckedTxt = root.findViewById<TextView>(R.id.last_checked_textView)

        projModelTxt.text = projectorModel
        projIpAddress.text = projectorIpAddress
        highLampHourTxt.text = highLampHour.toString()
        lowLampHourText.text = lowLampHour.toString()
        lastCheckedTxt.text = lastChecked

        return root;
    }
}