package com.erwinsuwito.qcapp.model

import java.time.LocalDateTime

data class Classroom(var className: String,
                     var projectorId: String,
                     var ipAddress: String,
                     var lampHourLimit: Int,
                     var isChecked: Boolean,
                     var lastChecked: LocalDateTime)