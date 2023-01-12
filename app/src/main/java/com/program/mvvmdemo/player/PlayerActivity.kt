package com.program.mvvmdemo.player

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.program.mvvmdemo.R
import com.program.mvvmdemo.base.BaseActivity
import com.program.mvvmdemo.musicsList.MusicPresenter
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : BaseActivity(){

    private val TAG:String="PlayerActivity.class"

    private val playerPresenter by lazy {
        PlayerPresenter.instance
    }

    private val musicPresenter by lazy {
        MusicPresenter(this)
    }

    fun toFlowPage(view: View){
        startActivity(Intent(this,FlowPlayerControllerActivity::class.java))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
//        playerPresenter.registerCallback(this)
        initListener()
        initDataListener()
    }

    private val livePlayerObserver by lazy {
        LivePlayerStateObserver()
    }

    override fun onDestroy() {
        super.onDestroy()
        LivePlayerState.instance.removeObserver(livePlayerObserver)
    }

    class LivePlayerStateObserver:Observer<PlayerPresenter.PlayState>{
        override fun onChanged(t: PlayerPresenter.PlayState?) {
            println("播放器界面。。。。。live data-->当前状态--->$t")
        }

    }
    /**
     * 对数据进行监听
     */
    private fun initDataListener() {

        LivePlayerState.instance.observeForever(livePlayerObserver)

        playerPresenter.currentMusic.addListener(this) {
            //更新ui
            //音乐内容发生变化
            songTitle.text=it?.name
            Log.d(TAG, "initDataListener: 封面变化了。。。。${it?.cover}")

        }
        playerPresenter.currentPlayState.addListener(this) {
            when(it){
                PlayerPresenter.PlayState.PAUSE -> {
                    playerOrPaseBtn.text="播放"
                }
                PlayerPresenter.PlayState.PLAYING ->{
                    playerOrPaseBtn.text="暂停"
                }
            }
        }
    }

    private fun initListener() {
        playerOrPaseBtn.setOnClickListener {
            playerPresenter.doPlayOrPause()
        }
        playNextBtn.setOnClickListener {
            playerPresenter.playNext()
        }
        playPreBtn.setOnClickListener {
            playerPresenter.playPre()
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        playerPresenter?.unRegisterCallback(this)
//    }

//    override fun onTitleChange(title: String) {
//        songTitle?.text=title
//    }
//
//    override fun onProgressChange(current: Int) {
//
//    }
//
//    override fun onPlaying() {
//        //播放中，显示暂停
//        playerOrPaseBtn.text="暂停"
//    }
//
//    override fun onPlayerPause() {
//        //暂停，显示播放
//        playerOrPaseBtn.text="播放"
//    }
//
//    override fun onCoverChange(cover: String) {
//        Log.d(TAG, "onCoverChange: 封面更新")
//    }
}