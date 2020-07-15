package com.example.imgur.di.module

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.os.Build
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.example.imgur.api.ApiHeaders
import com.example.imgur.api.ApiServices
import com.example.imgur.api.URLFactory
import com.example.imgur.custom_exception.AuthenticationException
import com.example.imgur.custom_exception.ServerError

import com.squareup.picasso.BuildConfig

import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.Interceptor.Companion.invoke
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
class AppModule {

    val timeToConnect = 30L

    @Singleton
    @Provides
    fun provideService(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }


    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson,
        @Named("header") headerInterceptor: Interceptor,
        @Named("pre_validation") networkInterceptor: Interceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): Retrofit {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return Retrofit.Builder()
            .client(
                okHttpClient.newBuilder()
                    .addInterceptor(headerInterceptor)
                    .addInterceptor(httpLoggingInterceptor)
                    .addNetworkInterceptor(networkInterceptor)
                    .build()
            )
            .baseUrl(URLFactory.provideHttpUrl())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideSimpleOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(timeToConnect, TimeUnit.SECONDS)
            .readTimeout(timeToConnect, TimeUnit.SECONDS)
            .writeTimeout(timeToConnect, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    @Named("header")
    fun provideHeaderInterceptor(): Interceptor {
        return invoke { chain ->
            val build = chain.request().newBuilder()
                .addHeader(ApiHeaders.TOKEN, ApiHeaders.TOKEN_ID)
                .build()
            chain.proceed(build)
        }
    }

    @Provides
    @Singleton
    @Named("pre_validation")
    fun provideNetworkInterceptor(): Interceptor {
        return invoke { chain ->
            val response = chain.proceed(chain.request())
            val code = response.code
            if (code >= 500)
                throw ServerError("Unknown server error", response.body!!.string())
            else if (code == 401 || code == 403)
                throw AuthenticationException()
            response
        }
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setPrettyPrinting().setVersion(1.0).create()
    }

    @Provides
    @Singleton
    @Named("preferences")
    fun providePreferencesName(): String {
        return "" //context.getString(R.string.preferences_name)
    }

    @Provides
    @Singleton
    @Named("api-key")
    fun provideApiKey(): String {
        return ""//context.getString(R.string.api_key)
    }

    @Provides
    @Singleton
    @Named("enc_key")
    fun provideEncKey(): String {
        return ""//context.getString(R.string.enc_key)
    }

    @Provides
    @Singleton
    @Named("enc_iv")
    fun provideIvKey(): String {
        return ""//context.getString(R.string.enc_iv)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideResources(application: Application): Resources {
        return application.resources
    }


}
