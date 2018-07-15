package com.example.keshavaggarwal.curofyassignment.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.keshavaggarwal.curofyassignment.config.CustomViewModelFactory
import com.example.keshavaggarwal.curofyassignment.vm.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by KeshavAggarwal on 14/07/18.
 */

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindsLoginViewModel(loginViewModel: LoginViewModel) : ViewModel

    @Binds
    internal abstract fun bindsViewModelFactory(customViewModelFactory: CustomViewModelFactory) : ViewModelProvider.Factory
}