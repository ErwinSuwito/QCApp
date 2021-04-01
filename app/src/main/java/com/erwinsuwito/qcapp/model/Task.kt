package com.erwinsuwito.qcapp.model

import java.time.LocalDateTime

class Task {
    lateinit var taskTitle: String
    lateinit var taskId: String
    lateinit var postedBy: String
    lateinit var postDate: LocalDateTime
    lateinit var closedDate: LocalDateTime
    lateinit var dueDate: LocalDateTime
    var isClosed: Boolean = false
}