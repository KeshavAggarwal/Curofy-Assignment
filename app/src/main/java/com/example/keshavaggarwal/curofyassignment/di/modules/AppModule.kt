package com.example.keshavaggarwal.curofyassignment.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.keshavaggarwal.curofyassignment.data.APIEndpoints
import com.example.keshavaggarwal.curofyassignment.data.prefs.SharedPrefHelper
import com.example.keshavaggarwal.curofyassignment.data.prefs.SharedPrefInterface
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by KeshavAggarwal on 14/07/18.
 */

@Module(includes = [(ViewModelModule::class)])
class AppModule {

    // providing the Http client to the retrofit
    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application): OkHttpClient {
        val cacheDir = File(application.cacheDir, UUID.randomUUID().toString())
        // 10 MiB cache
        val cache = Cache(cacheDir, 10 * 1024 * 1024)

        return OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    // building the retrofit instance here with OkHttp client as param
    @Provides
    @Singleton
    fun provideRetrofitBuilder(okHttpClient : OkHttpClient) : Retrofit {
        return Retrofit.Builder()
                .baseUrl("https://dev.curofy.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    // provides the webServiceInterface with retrofit as param
    @Provides
    @Singleton
    fun provideWebServiceInterface(retrofit: Retrofit): APIEndpoints {
        return retrofit.create(APIEndpoints::class.java)
    }

    // provides shared preferences
    @Provides
    @Singleton
    fun provideSharedPreference(context: Application) : SharedPreferences {
        return context.getSharedPreferences("CUROFY_ANDROID_APP_SHARED_PREF", Context.MODE_PRIVATE)
    }

    // provides preference interface which contains all the pref related methods
    @Provides
    @Singleton
    fun providePreferenceInterface(preferenceHelper : SharedPrefHelper) : SharedPrefInterface {
        return preferenceHelper
    }
}