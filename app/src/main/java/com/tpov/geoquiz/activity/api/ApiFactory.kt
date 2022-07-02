package com.tpov.geoquiz.activity.api

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private const val BASE_URL = "http://jservice.io/"
    // private const val BASE_URL_LANG = "https://translated-mymemory---translation-memory.p.rapidapi.com/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL)
        .build()

    /*private val retrofitLang = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL_LANG)
        .build()*/

    val apiService = retrofit.create(ApiService::class.java)
    //val apiServiceLang = retrofitLang.create(ApiServiceLang::class.java)
}
