package com.erwinsuwito.qcapp.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ClassCheck(
    var checkId: String = "",
    var checkedBy: String = "",
    var checkedByName: String = "",
    var classroomName: String = "",
    var highLampHour: Int = 0,
    var lowHighLampHour: Int = 0,
    var isAlignmentOk:  Boolean = false,
    var isClarityOk: Boolean = false,
    var isScreenOk: Boolean = false,
    var isRemoteOk: Boolean = false,
    var isHdmiOk: Boolean = false,
    var isEasyMpOk: Boolean = false,
    var isSwitchLabelOk: Boolean = false,
    var isGuidelineOk: Boolean = false,
    var isApOn: Boolean = false,
    var apSignalStrength: Int = 0,
    var pingGoogle: Int = 0,
    var dlSpeed: Double = 0.0,
    var ulSpeed: Double = 0.0,
    var ipAddress: String = "",
    var isPowerExtAvailable: Boolean = false,
    var checkedOn: Timestamp = Timestamp.now(),
) : Parcelable