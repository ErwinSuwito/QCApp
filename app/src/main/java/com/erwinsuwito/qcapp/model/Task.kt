package com.erwinsuwito.qcapp.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(var issueId: String = "",
                var classroom: String = "",
                var creator: String = "",
                var creatorName: String = "",
                var taskDetails: String = "",
                var taskTitle: String = "",
                var openedOn: Timestamp = Timestamp.now(),
                var closedOn: Timestamp = Timestamp.now(),
                var closedBy: String = "",
                var closedByName: String = "",
                var isOpen: Boolean = true) : Parcelable {
}