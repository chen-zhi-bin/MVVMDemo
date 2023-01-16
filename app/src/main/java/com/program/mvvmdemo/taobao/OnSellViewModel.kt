package com.program.mvvmdemo.taobao

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.program.mvvmdemo.domain.OnSellData
import kotlinx.coroutines.launch

class OnSellViewModel:ViewModel() {

    private val onSellRepository by lazy {
        OnSellRepository()
    }

    //当前页
    private var mCurrentPage=DEFAULT_PAGE

    companion object{
        //默认第一页
        const val DEFAULT_PAGE = 1
    }

    /**
     * 加载更多
     */
    fun loaderMore(){

    }

    /**
     * 加载首页内容
     */
    fun loadContent(){
        this.listContentByPage(mCurrentPage)
    }

    private fun listContentByPage(page:Int){
        viewModelScope.launch {
            val onSellList:OnSellData = onSellRepository.getOnSellList(page)
            println("result size =="+onSellList.tbk_dg_optimus_material_response.result_list.map_data.size)
        }
    }

    val contentList = MutableLiveData<MutableList<String>>()

}