package com.erwinsuwito.qcapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Classroom(var className: String,
                     var projectorId: String,
                     var ipAddress: String,
                     var lampHourLimit: Int,
                     var isChecked: Boolean,
                     var lastChecked: LocalDateTime) : Parcelable