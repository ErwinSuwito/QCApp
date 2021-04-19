package com.erwinsuwito.qcapp

import com.erwinsuwito.qcapp.model.*
import java.lang.Exception

object AppState {
    lateinit var upn: String;
    lateinit var fullName: String;
    lateinit var role: String;
    lateinit var lastException: Exception;
    lateinit var selectedClass: Classroom;
    lateinit var selectedIssue: Issue;

    lateinit var classList: MutableList<Classroom>;
    lateinit var projectorList: MutableList<Projector>;
    lateinit var scheduleList: MutableList<Schedule>;
}