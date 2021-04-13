package com.erwinsuwito.qcapp.model

class Classroom(var className: String) {
    lateinit var projector: Projector
    var isChecked: Boolean = false

    constructor(className: String, projectorId: String) : this(className)
    {
        // TODO: Find matching projector and assign it to projector object
    }
}