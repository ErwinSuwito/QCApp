package com.erwinsuwito.qcapp.model

import com.google.firebase.ktx.Firebase
import java.time.LocalDateTime

class Steps(var author: String, var authorName: String, var content: String,
            var addedOn: LocalDateTime = LocalDateTime.now(),
            var clossesIssue: Boolean = false) {
     var stepId: Int? = null
}