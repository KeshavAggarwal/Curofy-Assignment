package com.example.keshavaggarwal.curofyassignment.di.builder

import com.example.keshavaggarwal.curofyassignment.LoginActivity
import com.example.keshavaggarwal.curofyassignment.MainActivity
import com.example.keshavaggarwal.curofyassignment.OtpActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by KeshavAggarwal on 14/07/18.
 */

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract fun otpActivity() : OtpActivity

    @ContributesAndroidInjector
    abstract fun mainActivity() : MainActivity

    @ContributesAndroidInjector
    abstract fun loginActivity() : LoginActivity
}