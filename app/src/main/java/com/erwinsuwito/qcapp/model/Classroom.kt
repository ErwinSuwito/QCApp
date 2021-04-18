package com.erwinsuwito.qcapp.model

import com.erwinsuwito.qcapp.AppState
import com.erwinsuwito.qcapp.apis.FirestoreHelper
import java.time.LocalDateTime

class Classroom(var className: String) {
    lateinit var projector: Projector
    var isChecked: Boolean = false

    constructor(className: String, projectorId: String) : this(className)
    {
        // TODO: Find matching projector and assign it to projector object
        for (item in AppState.projectorList)
        {
            if (projectorId == item.projectorId)
            {
                this.projector = item
            }
        }
    }
}