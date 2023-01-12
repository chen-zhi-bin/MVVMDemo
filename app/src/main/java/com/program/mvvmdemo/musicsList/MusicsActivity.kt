package com.program.mvvmdemo.musicsList

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import com.program.mvvmdemo.R
import com.program.mvvmdemo.base.BaseActivity
import com.program.mvvmdemo.player.domain.Music
import kotlinx.android.synthetic.main.activity_musics.*

/**
 * activity的生命周期
 *  - onCreate  - 创建 -- 还不可见
 *  - onStart   - 可见 - 还不可以交互
 *  - onResume  - 可见 - 可以交互
 *  - onPause   -可见 -不可以交互
 *  - onStop    - 不可见   -不可以交互
 *  - onDestroy -销毁 --不可见
 *
 *  启动Activity
 *      onCreate --> onStart -->onResume        可见可交互
 *      按返回键(如果没有处理返回键，就是退出)
 *      onPause --> onStop -->onResume 可见可交互
 *      =========================================================================
 *       *  启动Activity
 *      onCreate --> onStart -->onResume        可见可交互
 *      按Home键
 *      onPause -->onStop
 *      ===========================================
 *       *  启动Activity
 *      onCreate --> onStart -->onResume        可见可交互
 *      启动另外一个Activity
 *       onPause -->onStop
 *    ===========================================
 *      启动透明的Activity，或者弹出Dialog
 *      onCreate --> onStart -->onResume        可见可交互
 *      启动另外一个Activity
 *       onPause 
 */
class MusicsActivity : BaseActivity() {

    private lateinit var mForeverObserver: ForeverObserver

    private val musicPresenter by lazy {
        MusicPresenter(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_musics)
        initDataListener()
        initViewListener()
//        musicPresenter.onCreate()
    }

//    override fun onStart() {
//        super.onStart()
//        musicPresenter.onStart()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        musicPresenter.onResume()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        musicPresenter.onPause()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        musicPresenter.onStop()
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        musicPresenter.onDestroy()
//    }


    inner class ForeverObserver:Observer<List<Music>>{
        override fun onChanged(result: List<Music>?) {
            println("forever observer data change -->${result?.size}")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        musicPresenter.liveMusicList.removeObserver(mForeverObserver)
    }
    /**
     * 监听数据变化
     */
    @SuppressLint("SetTextI18n")
    private fun initDataListener() {
        mForeverObserver=ForeverObserver()
        musicPresenter.liveMusicList.observeForever(mForeverObserver)
//        musicPresenter.liveMusicList.observe(
//            this, Observer {
//                //todo:去更新ui
//                println("live data 里的音乐列表数据更新。。。")
//            }
//        )
        musicPresenter.musicList.addListener(this) {
            println(Thread.currentThread().name)
            //数据变化
            println("结果 ---> ${it?.size}")
            musicCountText?.text="加载到了 ---> ${it?.size} 条数据"
        }
        musicPresenter.loadState.addListener(this) {
            println("加载状态 -->$it")
        }
    }

    private fun initViewListener() {
        getMusicBtn.setOnClickListener {
            musicPresenter.getMusic()
        }
    }


}