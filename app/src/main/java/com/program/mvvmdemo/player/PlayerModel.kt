package com.program.mvvmdemo.player

import com.program.mvvmdemo.player.domain.Music

class PlayerModel {
    fun getMusicById(id: String) :Music{
        return Music(
            "歌曲..$id",
            "https://images.sunofbeaches.com/content/2021_06_09/852231982405386240.png",
            "https://www.sunofbeaches.net"
        )
    }

}