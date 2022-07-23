package com.tpov.schoolquiz.activity.api

import com.tpov.schoolquiz.pojo.Responce
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

//http://jservice.io/api/random1
    @GET("api/random")
    suspend fun getFullPriceList(
        @Query(QUERY_COUNT) count: String = COUNT
    ): List<Responce>

    companion object {
        private const val COUNT = "1"
        private const val QUERY_COUNT = "count"
    }
}