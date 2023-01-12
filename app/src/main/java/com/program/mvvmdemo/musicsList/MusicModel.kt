package com.program.mvvmdemo.musicsList

import com.program.mvvmdemo.player.domain.Music

class MusicModel {

    fun loadMusicByPage(page: Int, size: Int, callback: OnMusicLoadResult) {
        val result: ArrayList<Music> = arrayListOf<Music>()
        Thread {
            for (i in (0 until size)) {
                Thread.sleep(200)
                result.add(
                    Music(
                        "音乐名称：$i",
                        "cover 封面$i",
                        "url == $i"
                    )
                )
            }
            //数据请求完成
            //通知数据更新
            callback.onSuccess(result)
        }.start()
    }

    interface OnMusicLoadResult {
        fun onSuccess(result: List<Music>)
        fun onError(msg: String, code: Int)
    }

}