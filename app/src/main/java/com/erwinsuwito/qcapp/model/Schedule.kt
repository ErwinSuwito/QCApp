package com.erwinsuwito.qcapp.model

import java.time.LocalDateTime
import java.time.DayOfWeek

class Schedule (var INTAKE: String, var MODID: String, var DAY: String, var LOCATION: String,
                var ROOM: String, var LECTID: String, var NAME: String, var SAMACCOUNTNAME: String,
                var DATESTAMP: String, var DATESTAMP_ISO: String, var TIME_FROM: String,
                var TIME_TO: String, var GROUPING: String, var COLOR: String) {

    lateinit var startTime: LocalDateTime
    lateinit var endTime: LocalDateTime
    lateinit var day: DayOfWeek

    init {
        day = DayOfWeek.valueOf(DAY)

    }
}