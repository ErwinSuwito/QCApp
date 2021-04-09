package com.erwinsuwito.qcapp.model

data class Classroom(var className: String) : IModel {
    lateinit var projector: Projector
    var isChecked: Boolean = false

    constructor(className: String, projectorId: String) : this(className)
    {
        // TODO: Find matching projector and assign it to projector object
    }

    override fun create() : Boolean
    {
        // TODO: Add code to save to database
        return false
    }

    override fun delete(): Boolean {
        // TODO: Add code to delete from database
        return false
    }

    override fun update(): Boolean {
        // TODO: Add code to update database data
        return false
    }
}