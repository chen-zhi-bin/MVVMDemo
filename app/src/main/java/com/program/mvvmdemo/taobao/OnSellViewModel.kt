package com.program.mvvmdemo.taobao

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.program.mvvmdemo.base.LoadState
import com.program.mvvmdemo.taobao.domain.OnSellData
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.NullPointerException

class OnSellViewModel:ViewModel() {

    val contentList = MutableLiveData<List<OnSellData.TbkDgOptimusMaterialResponse.ResultList.MapData>>()

    val loadState = MutableLiveData<LoadState>()

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
        loadState.value = LoadState.LOADING
        viewModelScope.launch {
            try {
                val onSellList:OnSellData = onSellRepository.getOnSellList(page)
                println("result size =="+onSellList.tbk_dg_optimus_material_response.result_list.map_data.size)
                contentList.value = onSellList.tbk_dg_optimus_material_response.result_list.map_data
                if (onSellList.tbk_dg_optimus_material_response.result_list.map_data.isEmpty()) {
                    loadState.value=LoadState.EMPTY
                }else{
                    loadState.value=LoadState.SUCCESS
                }
            }catch (e:Exception){
                e.printStackTrace()
                if (e is NullPointerException) {
                    //todo:没有更多内容的时候，会有一个空指针
                }else{
                    loadState.value = LoadState.ERROR
                }

            }

        }
    }


}