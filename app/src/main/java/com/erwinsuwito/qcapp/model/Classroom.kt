package com.erwinsuwito.qcapp.model

data class Classroom(var className: String, var projectorId: String, var ipAddress: String, var lampHourLimit: Int, var isChecked: Boolean = false)