package com.erwinsuwito.qcapp.ui.more

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.erwinsuwito.qcapp.R
import com.erwinsuwito.qcapp.adapter.MoreItemsAdapter
import com.erwinsuwito.qcapp.model.MoreItem

class MoreViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is more Fragment"
    }
    val text: LiveData<String> = _text
}