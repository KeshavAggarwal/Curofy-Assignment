package com.example.keshavaggarwal.curofyassignment.di.component

import android.app.Application
import com.example.keshavaggarwal.curofyassignment.BaseApplication
import com.example.keshavaggarwal.curofyassignment.di.builder.ActivityBuilder
import com.example.keshavaggarwal.curofyassignment.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by KeshavAggarwal on 14/07/18.
 */
@Singleton
@Component(modules = [(AndroidInjectionModule::class), (AndroidSupportInjectionModule::class),(AppModule::class), (ActivityBuilder::class)])

interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(app : Application) : Builder
        fun build(): AppComponent
    }

    fun inject(app : BaseApplication)
}
