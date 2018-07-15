package com.example.keshavaggarwal.curofyassignment.vm

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.text.TextUtils
import android.util.Log
import com.example.keshavaggarwal.curofyassignment.data.LoginResponse
import com.example.keshavaggarwal.curofyassignment.data.OtpResponse
import com.example.keshavaggarwal.curofyassignment.data.PostMobileRepo
import com.example.keshavaggarwal.curofyassignment.data.PostOtpRepo
import com.example.keshavaggarwal.curofyassignment.utils.Resource
import javax.inject.Inject

/**
 * Created by KeshavAggarwal on 14/07/18.
 */
class LoginViewModel @Inject
constructor(val postmobileRepo : PostMobileRepo, val postOtpRepo: PostOtpRepo) : ViewModel(){

    //inputs
    lateinit var phone : String
    lateinit var code : String
    lateinit var otp : String
    lateinit var sessionId : String

    //outputs
    var phoneErrorObservable = ObservableField<String>()
    var isPhoneValid = ObservableField<Boolean>()


    var postMobileObservable : MutableLiveData<Resource<OtpResponse>>
    var postOtpObservable : MutableLiveData<Resource<LoginResponse>>

    init {
        postMobileObservable = postmobileRepo.data
        postOtpObservable = postOtpRepo.data
    }

    private fun onSignUpClicked(){
        if(TextUtils.isEmpty(phone) || phone.length < 10){
            isPhoneValid.set(false)
            phoneErrorObservable.set("Please enter the valid phone number")
        }
        else{
            isPhoneValid.set(true)
            sendMobileToServer()
        }
    }

    // calling this function from LoginActivity to set the code and phone field here
    fun setCredentials(phone: String, code : String){
        this.phone = phone
        this.code = code
        onSignUpClicked()
    }

    fun setOtpCredentials(phone: String, code : String, otp : String, sessionId: String){
        this.phone = phone
        this.code = code
        this.otp = otp
        this.sessionId = sessionId
        sendOtpToServer()
    }

    // LoginActivity is observing this LiveData<Resource<LoginResponse>>
    fun getPostMobileObservable() : LiveData<Resource<OtpResponse>> {
        return postMobileObservable
    }

    fun getPostOtpObservable() : LiveData<Resource<LoginResponse>> {
        return postOtpObservable
    }

    fun sendMobileToServer(){
        postMobileObservable.value = postmobileRepo.postMobile(phone, "+$code").value
    }

    fun sendOtpToServer(){
        postOtpObservable.value = postOtpRepo.postOtp(phone, code, otp, sessionId).value
    }

}