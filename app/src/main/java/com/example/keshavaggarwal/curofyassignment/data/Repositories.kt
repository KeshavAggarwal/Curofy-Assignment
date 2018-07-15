package com.example.keshavaggarwal.curofyassignment.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.example.keshavaggarwal.curofyassignment.utils.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by KeshavAggarwal on 14/07/18.
 */


class PostMobileRepo @Inject
constructor(private val apiService: APIEndpoints){

    val data = MutableLiveData<Resource<OtpResponse>>()

    fun postMobile(mobile : String, countryCode: String) : LiveData<Resource<OtpResponse>>{
        data.value = Resource.loading(null)

        val call = apiService.postMobile(mobile, countryCode)
        call.enqueue(object : Callback<OtpResponse>{
            override fun onFailure(call: Call<OtpResponse>?, t: Throwable?) {
                data.value = Resource.error(t?.message!!, null)
            }

            override fun onResponse(call: Call<OtpResponse>?, response: Response<OtpResponse>?) {
                if (response!!.isSuccessful){
                    data.value = Resource.success(response.body()!!)
                }
                else{
                    data.value = Resource.error("Something went wrong", null)
                }
            }

        })

        return data
    }
}

class PostOtpRepo @Inject
constructor(private val apiService: APIEndpoints){

    val data = MutableLiveData<Resource<LoginResponse>>()

    fun postOtp(mobile : String, countryCode: String, otp: String, sessionId: String) : LiveData<Resource<LoginResponse>>{
        data.value = Resource.loading(null)

        val call = apiService.postOtp(mobile, countryCode, otp, sessionId)
        call.enqueue(object : Callback<LoginResponse>{
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                data.value = Resource.error(t?.message!!, null)
            }

            override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>?) {
                if (response!!.isSuccessful){
                    data.value = Resource.success(response.body()!!)
                }
                else{
                    data.value = Resource.error("Something went wrong", null)
                }
            }

        })

        return data
    }


}