package com.erwinsuwito.qcapp.model

class ClassCheck {
    lateinit var checkedBy: String
    lateinit var classId: String
    var highLampHour: Int = 0
    var lowHighLampHour: Int = 0
    var isAlignmentOk:  Boolean = false
    var isClarityOk: Boolean = false
    var isScreenOk: Boolean = false
    var isProjectorRemoteOk: Boolean = false
    var isShortHdmiOk: Boolean? = null
    var isLongHdmiOk: Boolean? = null
    var isEasyMPOk: Boolean? = null
    var isSwitchLabelOk: Boolean = false
    var isGuidelineOk: Boolean = false
    var isApOn: Boolean = false
    var apSignalStrength: Int = 0
    var pingGoogle: Int = 0
    var dlSpeed: Float = 0F
    var ulSpeed: Float = 0F
    lateinit var ipAddress: String
    var isPowerExtAvailable: Boolean = false
}