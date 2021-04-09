package com.erwinsuwito.qcapp.model

data class Projector (var projectorId: String, var ipAddress: String, var lampHourLimit: Int) : IModel {
    var highLampHour: Int = 0
    var lowHighLampHour: Int = 0

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