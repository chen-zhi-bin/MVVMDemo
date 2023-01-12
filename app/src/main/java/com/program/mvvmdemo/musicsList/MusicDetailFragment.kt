package com.program.mvvmdemo.musicsList

import com.program.mvvmdemo.base.BaseFragment

class MusicDetailFragment:BaseFragment() {



    private val musicPresenter by lazy {
        MusicPresenter(this)
    }


}