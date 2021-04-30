package com.erwinsuwito.qcapp.model

import java.time.LocalDateTime

class Task(var classroom: Classroom, var creator: String, var creatorName: String,
           var problem: String, var taskTitle: String, var openedOn: LocalDateTime = LocalDateTime.now(),
           var isOpen: Boolean = true, var isTask: Boolean = false) {

    lateinit var issueId: String
    lateinit var closedOn: LocalDateTime
    lateinit var closedBy: String

}