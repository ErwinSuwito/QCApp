package com.erwinsuwito.qcapp.model

import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

data class Steps(var stepId: Int,
                 var author: String,
                 var authorName: String,
                 var content: String,
                 var addedOn: LocalDateTime,
                 var clossesIssue: Boolean) {
}