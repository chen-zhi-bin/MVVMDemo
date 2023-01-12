package com.program.mvvmdemo.player

import androidx.lifecycle.LiveData

class LivePlayerState private constructor():LiveData<PlayerPresenter.PlayState>() {

    public override fun postValue(value: PlayerPresenter.PlayState?) {
        super.postValue(value)

    }

    override fun setValue(value: PlayerPresenter.PlayState?) {
        super.setValue(value)
    }

    companion object{
        val instance by lazy {
            LivePlayerState()
        }
    }

}