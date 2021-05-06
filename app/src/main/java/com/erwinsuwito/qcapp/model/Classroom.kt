package com.erwinsuwito.qcapp.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Classroom(var className: String = "",
                     var projectorId: String = "",
                     var ipAddress: String = "",
                     var highLampHour: Int = 0,
                     var lowLampHour: Int = 0,
                     var isChecked: Boolean = false,
                     var lastChecked: Timestamp = Timestamp.now()) : Parcelable