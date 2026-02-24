package org.delcom.pam_p4_ifs23051.network.flower.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.delcom.pam_p4_ifs23051.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class FlowerLanguageAppContainer : IFlowerLanguageAppContainer {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
        else HttpLoggingInterceptor.Level.NONE
    }

    private val okHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
        connectTimeout(2, TimeUnit.MINUTES)
        readTimeout(2, TimeUnit.MINUTES)
        writeTimeout(2, TimeUnit.MINUTES)
    }.build()

    // URL backend Bahasa Bunga (pam-2026-p4-ifs23051-be) — ganti setelah backend siap
    private val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL_FLOWER_API)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private val retrofitService: FlowerLanguageApiService by lazy {
        retrofit.create(FlowerLanguageApiService::class.java)
    }

    override val flowerLanguageRepository: IFlowerLanguageRepository by lazy {
        FlowerLanguageRepository(retrofitService)
    }
}