package com.erwinsuwito.qcapp.ui.qc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QcFragmentViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is QC Fragment"
    }
    val text: LiveData<String> = _text
}