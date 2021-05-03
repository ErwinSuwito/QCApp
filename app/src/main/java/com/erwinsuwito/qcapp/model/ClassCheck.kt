package com.erwinsuwito.qcapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ClassCheck(
    var checkId: String,
    var checkedBy: String,
    var checkedByName: String,
    var classId: String,
    var highLampHour: Int,
    var lowHighLampHour: Int,
    var isAlignmentOk:  Boolean,
    var isClarityOk: Boolean,
    var isScreenOk: Boolean,
    var isRemoteOk: Boolean,
    var isShortHdmiOk: Boolean,
    var isLongHdmiOk: Boolean,
    var isEasyMPOk: Boolean,
    var isSwitchLabelOk: Boolean,
    var isGuidelineOk: Boolean,
    var isApOn: Boolean,
    var apSignalStrength: Int,
    var pingGoogle: Int,
    var dlSpeed: Double,
    var ulSpeed: Double,
    var ipAddress: String,
    var isPowerExtAvailable: Boolean,
    var checkedOn: LocalDateTime,
    var isEverythingOk: Boolean
) : Parcelable