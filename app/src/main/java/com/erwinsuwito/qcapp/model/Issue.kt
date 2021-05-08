package com.erwinsuwito.qcapp.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Issue(var issueId: String = "",
                 var classroomName: String = "",
                 var creator: String = "",
                 var creatorName: String = "",
                 var problem: String = "",
                 var openedOn: Timestamp = Timestamp.now(),
                 var closedOn: Timestamp = Timestamp.now(),
                 var closedBy: String = "",
                 var closedByName: String = "",
                 var isOpen: Boolean = false) : Parcelable {
}