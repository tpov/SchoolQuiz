package com.tpov.geoquiz.activity.api

import com.tpov.geoquiz.pojo.Responce
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {


    @GET("api/random")
    suspend fun getFullPriceList(
        @Query(QUERY_COUNT) count: String = COUNT
    ): List<Responce>

    companion object {
        private const val COUNT = "1"
        private const val QUERY_COUNT = "count"
    }
}