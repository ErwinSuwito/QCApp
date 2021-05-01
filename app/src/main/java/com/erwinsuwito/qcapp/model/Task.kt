package com.erwinsuwito.qcapp.model

import java.time.LocalDateTime

data class Task(var issueId: String, var classroom: String, var creator: String, var creatorName: String,
           var problem: String, var taskTitle: String, var openedOn: LocalDateTime = LocalDateTime.now(),
                var closedOn: LocalDateTime, var closedBy: String, var isOpen: Boolean = true) {
}