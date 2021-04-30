package com.erwinsuwito.qcapp.model

import java.time.LocalDateTime

class Issue(var classroom: String, var creator: String, var creatorName: String, var problem: String,
            var openedOn: LocalDateTime = LocalDateTime.now(), var isOpen: Boolean = true) {

    lateinit var issueId: String
    lateinit var closedOn: LocalDateTime
    lateinit var closedBy: String

    constructor(classroom: String, creator: String, creatorName: String, problem: String,
                openedOn: LocalDateTime = LocalDateTime.now(), isOpen: Boolean = true,
                issueId: String, closedOn: LocalDateTime, closedBy: String,
                isTask: Boolean = false) : this(classroom, creator, creatorName, problem, openedOn, isOpen) {
                    this.issueId = issueId
                    this.closedOn = closedOn
                    this.closedBy = closedBy
                }
}