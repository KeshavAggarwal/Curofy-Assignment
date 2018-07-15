package com.example.keshavaggarwal.curofyassignment

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Created by KeshavAggarwal on 13/07/18.
 */
abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity(), HasSupportFragmentInjector{

    @Inject
    lateinit var fragmentAndroidInjector: DispatchingAndroidInjector<Fragment>

    lateinit var dataBinding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, getLayoutRes())
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun isNetworkConnected(): Boolean {
        return isNetworkAvailable(applicationContext)
    }

    // checking the network availability
    fun isNetworkAvailable(context: Context): Boolean {
        try {
            val connManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isAvailable && networkInfo.isConnected) {
                return true
            }
        } catch (ex: Exception) {
            return false
        }

        return false
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentAndroidInjector

}