package com.githubexample.android.retrofitClient

import com.githubexample.android.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClientSingleton {

    companion object {
        val TAG = RetrofitClientSingleton::class.java.simpleName
        fun getInstance(): ServiceGenerator {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpBuilder = OkHttpClient.Builder()

            if (BuildConfig.DEBUG) {
                okHttpBuilder.addInterceptor(loggingInterceptor)
//                okHttpBuilder.sslSocketFactory(sslContext.socketFactory,trustManager)
            }

            okHttpBuilder.readTimeout(3, TimeUnit.MINUTES)
            okHttpBuilder.connectTimeout(3, TimeUnit.MINUTES)
            okHttpBuilder.writeTimeout(3, TimeUnit.MINUTES)

            val client = okHttpBuilder.build()


            val retrofit = Retrofit.Builder().baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

            return retrofit.create(ServiceGenerator::class.java)
        }
    }

}