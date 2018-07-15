package com.example.keshavaggarwal.curofyassignment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.keshavaggarwal.curofyassignment.adapters.SliderAdapter
import com.example.keshavaggarwal.curofyassignment.data.prefs.SharedPrefInterface
import com.example.keshavaggarwal.curofyassignment.databinding.ActivityLoginBinding
import com.example.keshavaggarwal.curofyassignment.utils.Status
import com.example.keshavaggarwal.curofyassignment.vm.LoginViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding>(){

    override fun getLayoutRes(): Int {
        return R.layout.activity_login
    }

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory
    @Inject lateinit var sp : SharedPrefInterface

    lateinit var vm : LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        if (sp.getUserLoggedIn()){
            startActivity(Intent(this, MainActivity::class.java))
        }

        vm = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
        dataBinding.vm = vm

        setSliderAdapter()
        setCountrySpinner()
        setCountryCodeSpinner()

        dataBinding.btnGetStarted.setOnClickListener {
            if (isNetworkConnected())
                vm.setCredentials(etv_number.text.toString(), spinner_country_code.selectedItem.toString())
            else{
                showSnackbar(resources.getString(R.string.no_network_string))
            }
        }
        observerPostMobileObservable()
    }

    private fun observerPostMobileObservable() {
        vm.getPostMobileObservable().observe(this, Observer {
            dataBinding.resourcenew = it
            if (it != null){
                when(it.status){
                    Status.LOADING -> {
                        // do nothing, data binding will take of showing progressbar
                    }
                    Status.SUCCESS -> {
                        val intent = Intent(this@LoginActivity, OtpActivity::class.java)
                        intent.putExtra("KEY_SESSION_ID", it.data?.data?.session_id)
                        intent.putExtra("KEY_PHONE", dataBinding.etvNumber.text.toString())
                        intent.putExtra("KEY_CODE", dataBinding.spinnerCountryCode.selectedItem.toString())
                        startActivity(intent)
                    }
                    Status.ERROR -> {
                        showSnackbar(resources.getString(R.string.server_error))
                    }
                }
            }
        })

    }

    private fun setSliderAdapter() {
        val vp =  vp_pager
        vp.adapter = SliderAdapter()
    }

    private fun setCountryCodeSpinner() {
        val adapterCode = ArrayAdapter.createFromResource(this, R.array.country_code, android.R.layout.simple_spinner_item)
        adapterCode.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_country_code.adapter = adapterCode



        spinner_country_code.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }


            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinner_country.setSelection(p2)
            }

        }
    }

    private fun setCountrySpinner() {
        val adapterCountry = ArrayAdapter.createFromResource(this, R.array.country_arrays, android.R.layout.simple_spinner_item)
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner_country.adapter = adapterCountry

        spinner_country.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinner_country_code.setSelection(p2)
            }

        }
    }

    private fun showSnackbar(msg: String){
        Snackbar.make(dataBinding.scContainer, msg, Snackbar.LENGTH_SHORT).show()
    }

}
