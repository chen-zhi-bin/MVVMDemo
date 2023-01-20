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

    val contentList = MutableLiveData<MutableList<OnSellData.TbkDgOptimusMaterialResponse.ResultList.MapData>>()

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

    private var isLoaderMore = false

    /**
     * 加载更多
     */
    fun loaderMore(){
        isLoaderMore = true
        println("loadMore")
        loadState.value = LoadState.LOADER_MORE_LOADING
        //加载更多内容
        mCurrentPage++
        this.listContentByPage(mCurrentPage)
    }

    /**
     * 加载首页内容
     */
    fun loadContent(){
        isLoaderMore =false
        loadState.value = LoadState.LOADING
        this.listContentByPage(mCurrentPage)
    }

    private fun listContentByPage(page:Int){
        viewModelScope.launch {
            try {
                val onSellList:OnSellData = onSellRepository.getOnSellList(page)
                val oldValue = contentList.value?: mutableListOf()
                println("result size =="+onSellList.tbk_dg_optimus_material_response.result_list.map_data.size)
                oldValue.addAll(onSellList.tbk_dg_optimus_material_response.result_list.map_data)
                contentList.value=oldValue
                if (onSellList.tbk_dg_optimus_material_response.result_list.map_data.isEmpty()) {
                    mCurrentPage--
                    loadState.value=if (isLoaderMore)LoadState.LOADER_MORE_EMPTY else LoadState.EMPTY
                }else{
                    loadState.value=LoadState.SUCCESS
                }
            }catch (e:Exception){
                mCurrentPage--
                e.printStackTrace()
                if (e is NullPointerException) {
                    //没有更多内容的时候，会有一个空指针
                    loadState.value = LoadState.LOADER_MORE_EMPTY
                }else{
                    loadState.value = if (isLoaderMore)LoadState.LOADER_MORE_ERROR else LoadState.ERROR
                }

            }

        }
    }


}