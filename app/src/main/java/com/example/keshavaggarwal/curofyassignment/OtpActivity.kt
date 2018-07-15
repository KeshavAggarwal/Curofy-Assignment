package com.example.keshavaggarwal.curofyassignment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.LocalBroadcastManager
import android.text.TextUtils
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.keshavaggarwal.curofyassignment.data.prefs.SharedPrefInterface
import com.example.keshavaggarwal.curofyassignment.databinding.ActivityOtpBinding
import com.example.keshavaggarwal.curofyassignment.utils.Status
import com.example.keshavaggarwal.curofyassignment.vm.LoginViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject


/**
 * Created by KeshavAggarwal on 14/07/18.
 */
class OtpActivity : BaseActivity<ActivityOtpBinding>() {

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory
    @Inject lateinit var sp : SharedPrefInterface

    private lateinit var vm : LoginViewModel

    lateinit var sessionId : String
    lateinit var phone : String
    lateinit var code : String

    override fun getLayoutRes(): Int {
        return R.layout.activity_otp
    }

    private var mIsMessagePermissionsGranted = false
    private val MESSAGE_PERMISSION_REQUEST_CODE = 123
    val TAG = "Otp_Activity"


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        sessionId = intent.getStringExtra("KEY_SESSION_ID")
        phone = intent.getStringExtra("KEY_PHONE")
        code = intent.getStringExtra("KEY_CODE")

        vm = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        observerPostOtpObservable()

        dataBinding.llResendCode.setOnClickListener {
            vm.sendMobileToServer()
        }

    }

    private fun observerPostOtpObservable() {
        vm.getPostOtpObservable().observe(this, Observer {
            dataBinding.resourcenew = it
            if (it != null){
                when(it.status){
                    Status.LOADING -> {
                        // do nothing, data binding will take of showing progressbar
                    }
                    Status.SUCCESS -> {
                        val intent = Intent(this@OtpActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        sp.setUserLoggedIn(true)
                        finish()
                    }

                    Status.ERROR -> {
                        showSnackbar(resources.getString(R.string.server_error))
                    }
                }
            }
        })

    }

    private fun showSnackbar(msg: String){
        Snackbar.make(dataBinding.rlContainer, msg, Snackbar.LENGTH_SHORT).show()
    }


    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action!!.equals("otp", ignoreCase = true)) {
                val message = intent.getStringExtra("message")
                dataBinding.etvOtp1.setText(message.substring(0,1), TextView.BufferType.EDITABLE)
                dataBinding.etvOtp2.setText(message.substring(1,2), TextView.BufferType.EDITABLE)
                dataBinding.etvOtp3.setText(message.substring(2,3), TextView.BufferType.EDITABLE)
                dataBinding.etvOtp4.setText(message.substring(3,4), TextView.BufferType.EDITABLE)
                dataBinding.etvOtp4.requestFocus()

                vm.setOtpCredentials(phone, code, message, sessionId)
                Log.i(TAG, message.toString())
            }
        }
    }



    private fun askForMessagePermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED){
                mIsMessagePermissionsGranted = true
                registerReceiver()

            }
            else{
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_SMS, android.Manifest.permission.SEND_SMS, android.Manifest.permission.RECEIVE_SMS), MESSAGE_PERMISSION_REQUEST_CODE)
            }
        }
        else{
            registerReceiver()
        }
    }

    /*override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            MESSAGE_PERMISSION_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    registerReceiver()
                }
            }
        }
    }*/

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            MESSAGE_PERMISSION_REQUEST_CODE -> {
                if(grantResults.isNotEmpty()){
                    for(i in grantResults.indices){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mIsMessagePermissionsGranted = false
                            return
                        }
                    }
                    mIsMessagePermissionsGranted = true
                }
            }
        }
    }

    override fun onResume() {
        if (mIsMessagePermissionsGranted){
            registerReceiver()
        }
        else{
            askForMessagePermission()
        }
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver)
    }

    private fun registerReceiver(){
        LocalBroadcastManager.getInstance(this).
                registerReceiver(receiver, IntentFilter("otp"))
    }
}