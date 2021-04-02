package com.erwinsuwito.qcapp.model

import java.time.LocalDateTime

class Task(var taskTitle: String, var postedBy: String, var postDate: LocalDateTime,
           var dueDate: LocalDateTime = LocalDateTime.now(), var isClosed: Boolean = false) : IModel {

    lateinit var taskId: String
    lateinit var closedDate: LocalDateTime

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