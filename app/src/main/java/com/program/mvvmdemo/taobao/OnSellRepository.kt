package com.program.mvvmdemo.taobao

import com.program.mvvmdemo.taobao.api.RetrofitClient

class OnSellRepository {

    suspend fun getOnSellList(page:Int) =
        RetrofitClient.apiService.getOnSellList(page).apiData()

}