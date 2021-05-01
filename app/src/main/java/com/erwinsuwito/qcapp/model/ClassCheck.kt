package com.erwinsuwito.qcapp.model

data class ClassCheck(var checkedBy: String,
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
                      var dlSpeed: Float,
                      var ulSpeed: Float,
                      var ipAddress: String,
                      var isPowerExtAvailable: Boolean) {

}