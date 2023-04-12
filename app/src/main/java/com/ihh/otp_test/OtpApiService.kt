package com.ihh.otp_test

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface OtpApiService {

    @POST("generate-otp/")
    @FormUrlEncoded
    fun generateOtp(
        @Field("otp_key") otpKey: String
    ): Call<OtpResponse>
}