package com.example.keshavaggarwal.curofyassignment.data

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by KeshavAggarwal on 14/07/18.
 */
interface APIEndpoints {

    @FormUrlEncoded
    @POST("generate_otp.json")
    fun postMobile(@Field("mobile_no") mobile : String,
                   @Field("country_code") countryCode : String) : Call<OtpResponse>

    @FormUrlEncoded
    @POST("login_app.json")
    fun postOtp(@Field("mobile_no") mobile : String,
                @Field("country_code") countryCode : String,
                @Field("otp") otp : String,
                @Field("session_id") sessionId : String) : Call<LoginResponse>
}