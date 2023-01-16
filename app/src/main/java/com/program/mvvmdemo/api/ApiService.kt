package com.program.mvvmdemo.api

import com.program.mvvmdemo.domain.OnSellData
import com.program.mvvmdemo.domain.ResultData
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    companion object {
        const val BASE_URL="https://api.sunofbeaches.com/shop/"
    }

    @GET("onSell/{page}")
    suspend fun getOnSellList(@Path("page") page:Int): ResultData<OnSellData>
}