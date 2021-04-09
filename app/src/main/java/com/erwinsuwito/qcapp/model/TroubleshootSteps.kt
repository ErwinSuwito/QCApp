package com.erwinsuwito.qcapp.model

import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

data class TroubleshootSteps(var author: String, var authorName: String, var content: String,
                             var addedOn: LocalDateTime = LocalDateTime.now(),
                        var clossesIssue: Boolean = false) : IModel {

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