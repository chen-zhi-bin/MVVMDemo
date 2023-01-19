package com.program.mvvmdemo.taobao

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lcodecore.tkrefreshlayout.footer.LoadingView
import com.program.mvvmdemo.R
import com.program.mvvmdemo.base.LoadState
import com.program.mvvmdemo.taobao.adapter.OnSellListAdapter
import com.program.mvvmdemo.utils.SizeUtils
import kotlinx.android.synthetic.main.activity_on_error.*
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
            loadState.observe(this@OnSellActivity, Observer {
                //根据加载的状态来更新UI显示
                hideAll()
                println(it)
                when (it) {
                    LoadState.LOADING -> {
                        loadingView.visibility = View.VISIBLE
                    }
                    LoadState.EMPTY -> {
                        emptyView.visibility = View.VISIBLE
                    }
                    LoadState.ERROR -> {
                        errorView.visibility = View.VISIBLE
                    }
                    LoadState.SUCCESS -> {
                        contentListRv.visibility = View.VISIBLE
                    }
                }
            })
        }.loadContent()
    }

    /**
     * 初始化view
     */
    private fun initView() {
        reloadLL.setOnClickListener {
            //重新加载
            mViewModel.loadContent()
        }
        contentListRv.run {
            layoutManager = LinearLayoutManager(this@OnSellActivity)
            adapter =mAdapter
            addItemDecoration(
                object : RecyclerView.ItemDecoration(){
                    override fun getItemOffsets(
                        outRect: Rect,
                        view: View,
                        parent: RecyclerView,
                        state: RecyclerView.State
                    ) {
                        outRect.apply {
                            val padding:Int = SizeUtils.dip2px(this@OnSellActivity,4.0f)
                            top = padding
                            left = padding
                            bottom = padding
                            right = padding
                        }

                    }
                }
            )
        }
    }

    private fun hideAll(){
        contentListRv.visibility=View.GONE
        emptyView.visibility=View.GONE
        loadingView.visibility=View.GONE
        errorView.visibility=View.GONE
    }

}