package com.erwinsuwito.qcapp.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.firebase.ktx.Firebase
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Steps(var stepId: String = "",
                 var author: String = "",
                 var authorName: String = "",
                 var content: String = "",
                 var addedOn: Timestamp = Timestamp.now(),
                 var clossesIssue: Boolean = false) : Parcelable {
}