package com.erwinsuwito.qcapp.model

import java.time.LocalDateTime

class Issue(var classroom: Classroom, var creator: String, var creatorName: String, var problem: String,
            var openedOn: LocalDateTime = LocalDateTime.now(), var isOpen: Boolean = true, var isTask: Boolean = false) {

    lateinit var issueId: String
    lateinit var closedOn: LocalDateTime
    lateinit var closedBy: String

    constructor(classroom: Classroom, creator: String, creatorName: String, problem: String,
                openedOn: LocalDateTime = LocalDateTime.now(), isOpen: Boolean = true,
                issueId: String, closedOn: LocalDateTime, closedBy: String,
                isTask: Boolean = false) : this(classroom, creator, creatorName, problem, openedOn, isOpen, isTask) {
                    this.issueId = issueId
                    this.closedOn = closedOn
                    this.closedBy = closedBy
                }
}