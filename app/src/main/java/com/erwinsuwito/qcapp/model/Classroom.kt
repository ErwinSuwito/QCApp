package com.erwinsuwito.qcapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Classroom(var className: String,
                     var projectorId: String,
                     var ipAddress: String,
                     var highLampHour: Int,
                     var lowLampHour: Int,
                     var isChecked: Boolean,
                     var lastChecked: LocalDateTime) : Parcelable