package com.erwinsuwito.qcapp.model

import java.time.LocalDateTime

data class Issue(var classroom: Classroom, var creator: String, var creatorName: String, var problem: String,
            var openedOn: LocalDateTime = LocalDateTime.now(), var isOpen: Boolean = true, var isTask: Boolean = false) : IModel {

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

    override fun create() : Boolean
    {
        // TODO: Add code to save to database
        return false
    }

    override fun delete(): Boolean {
        // TODO: Add code to delete from database
        return false
    }

    override fun update(): Boolean {
        // TODO: Add code to update database data
        return false
    }
}