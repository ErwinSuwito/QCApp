package com.erwinsuwito.qcapp.model

import java.time.LocalDateTime

class Issue {
    lateinit var issueId: String
    lateinit var classroom: Classroom
    lateinit var creator: String
    lateinit var closedOn: LocalDateTime
    lateinit var openedOn: LocalDateTime
    lateinit var closedBy: String
    var isOpen: Boolean = true
}