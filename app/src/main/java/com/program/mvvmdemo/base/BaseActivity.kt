package com.program.mvvmdemo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.program.mvvmdemo.lifecycle.ILifecycleOwner
import com.program.mvvmdemo.lifecycle.LifeState
import com.program.mvvmdemo.lifecycle.LifecycleProvider

open class BaseActivity:AppCompatActivity(),ILifecycleOwner {

    val lifecycleProvider by lazy {
        LifecycleProvider()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      lifecycleProvider.makeLifeState(LifeState.CREATE)
    }

    override fun onStart() {
        super.onStart()
        lifecycleProvider.makeLifeState(LifeState.START)
    }

    override fun onResume() {
        super.onResume()
        lifecycleProvider.makeLifeState(LifeState.RESUME)
    }

    override fun onPause() {
        super.onPause()
        lifecycleProvider.makeLifeState(LifeState.PAUSE)
    }

    override fun onStop() {
        super.onStop()
        lifecycleProvider.makeLifeState(LifeState.STOP)
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycleProvider.makeLifeState(LifeState.DESTROY)
    }

    override fun getLifeProvider(): LifecycleProvider {
        return lifecycleProvider
    }
}