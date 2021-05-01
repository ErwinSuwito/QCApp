package com.erwinsuwito.qcapp.model

import java.time.LocalDateTime

data class Issue(var issueId: String,
                 var classroom: String,
                 var creator: String,
                 var creatorName: String,
                 var problem: String,
                 var openedOn: LocalDateTime,
                 var closedOn: LocalDateTime,
                 var closedBy: String,
                 var isOpen: Boolean) {
}