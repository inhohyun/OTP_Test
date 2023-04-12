package com.ihh.otp_test


import com.google.gson.annotations.SerializedName

data class OtpResponse(
    @SerializedName("otp_code") val otp_code: String
)