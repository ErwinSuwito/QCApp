package com.erwinsuwito.qcapp.model

import com.erwinsuwito.qcapp.AppState
import com.erwinsuwito.qcapp.apis.FirestoreHelper
import java.time.LocalDateTime

class Classroom(var className: String) {
    lateinit var projector: String
    var isChecked: Boolean = false

}