package com.program.mvvmdemo.player

import com.program.mvvmdemo.lifecycle.AbsLifecycle
import com.program.mvvmdemo.lifecycle.LifeState
import com.program.mvvmdemo.player.domain.Music

/**
 * 播放音乐
 * 暂停音乐
 * 上一首
 * 下一首
 * ===================
 * 播放状态：
 * - 通知ui改变成播放状态
 * - 通知ui进度变化
 * 上一首，下一首
 * - 通知ui歌曲标题变化
 * - 通知ui歌曲封面变化
 * 暂停音乐
 * - 更新ui状态为暂停
 *
 * 相关数据：
 * 当前播放的歌曲
 * 当前播放的状态
 */
class PlayerPresenter private constructor() : AbsLifecycle() {

    var currentMusic = DataListenContainer<Music>()

    var currentPlayState = DataListenContainer<PlayState>()

    var livePlayerState = LivePlayerState.instance

    private val player by lazy {
        MusicPlayer()
    }

    private val playerModel by lazy {
        PlayerModel()
    }

    companion object {
        val instance by lazy {
            PlayerPresenter()
        }
    }

    enum class PlayState {
        NONE, PLAYING, PAUSE, LOADING
    }

    private val callbacksList = arrayListOf<IPlayerCallback>()


//    fun registerCallback(callback: IPlayerCallback){
//        if (!callbacksList.contains(callback)) {
//            callbacksList.add(callback)
//        }
//    }

//    fun unRegisterCallback(callback: IPlayerCallback){
//        callbacksList.remove(callback)
//    }

    /**
     * 状态控制
     */
    fun doPlayOrPause() {
        if (currentMusic.value == null) {
            //去获取一首歌曲
            currentMusic.value = playerModel.getMusicById("卡农")
        }
        player.play(currentMusic.value)
//        dispatchTitleChange("当前播放的歌曲标题")
//        dispatchCoverChange("当前播放的歌曲封面")
        if (currentPlayState.value != PlayState.PLAYING) {
            //开始播放
//            dispatchPlayingState()
            currentPlayState.value = PlayState.PLAYING
            livePlayerState.postValue(PlayState.PLAYING)
        } else {
            //暂停
            currentPlayState.value = PlayState.PAUSE
            livePlayerState.postValue(PlayState.PAUSE)
//            dispatchPlayerPauseState()
        }
    }

    private fun dispatchPlayerPauseState() {
        callbacksList.forEach {
            it.onPlayerPause()
        }
    }

    private fun dispatchPlayingState() {
        callbacksList.forEach {
            it.onPlaying()
        }
    }

    /**
     * 下一首
     */
    fun playNext() {
        currentMusic.value = playerModel.getMusicById("下一首:ababab")
        //1.拿到下一首 -->变更UI（标题封面等）
//        dispatchTitleChange("切换到下一首，标题变化")
//        dispatchCoverChange("切换到下一首，封面变化")
        //2.设置给播放器  -->
        //3.等待播放的回调通知
        currentPlayState.value = PlayState.PLAYING
    }

//    private fun dispatchCoverChange(cover: String) {
//        callbacksList.forEach {
//            it.onCoverChange(cover)
//        }
//    }

//    private fun dispatchTitleChange(title:String) {
//        callbacksList.forEach {
//            it.onTitleChange(title)
//        }
//    }

    /**
     * 上一首
     */
    fun playPre() {
        currentMusic.value = playerModel.getMusicById("上一首:醒来")
//        dispatchTitleChange("切换到上一首，标题变化")
//        dispatchCoverChange("切换到上一首，封面变化")
        currentPlayState.value = PlayState.PLAYING
    }

    override fun onCreate() {

    }

    override fun onStart() {
        println("监听变化...")
    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {
        println("停止监听")
    }

    override fun onDestroy() {

    }

    override fun onviewLifeStateChange(state: LifeState) {

    }
}