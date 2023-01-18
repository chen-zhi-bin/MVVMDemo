package com.program.mvvmdemo.taobao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.program.mvvmdemo.R
import com.program.mvvmdemo.taobao.adapter.OnSellListAdapter
import kotlinx.android.synthetic.main.activity_onsell.*

class OnSellActivity : AppCompatActivity() {

    private val mViewModel by lazy {
        ViewModelProvider(this).get(OnSellViewModel::class.java)
    }

    private val mAdapter by lazy {
        OnSellListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onsell)
//        val viewModel:OnSellViewModel = ViewModelProvider(this).get(OnSellViewModel::class.java)
//        viewModel.loadContent()
        initView()
        initObserver()
    }

    /**
     * 观察数据变化
     */
    private fun initObserver() {
        mViewModel.apply {
            contentList.observe(this@OnSellActivity, Observer {
                //内容列表更新
                //跟新适配器
                mAdapter.setData(it)
            })
        }.loadContent()
    }

    /**
     * 初始化view
     */
    private fun initView() {
        contentListRv.run {
            layoutManager = LinearLayoutManager(this@OnSellActivity)
            adapter =mAdapter
        }
    }

}