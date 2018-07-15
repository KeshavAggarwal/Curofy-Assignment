package com.example.keshavaggarwal.curofyassignment.data

/**
 * Created by KeshavAggarwal on 14/07/18.
 */


data class OtpResponse(val status : Int, val data : DataOtpResponse)

data class DataOtpResponse(val session_id: String)

data class LoginResponse(val status: Int, val data: DataLoginResponse)

data class DataLoginResponse(val profile_exists: Int, val session_id: String)