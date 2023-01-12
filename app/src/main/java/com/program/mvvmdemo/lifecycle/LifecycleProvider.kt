package com.program.mvvmdemo.lifecycle

/**
 * 管理所注册进来的接口，这个接口就是ILifecycle
 * 保存当前view的生命周期
 */
class LifecycleProvider {

    private var currentLifeState:LifeState=LifeState.DESTROY
    private val lifecycleListener = arrayListOf<AbsLifecycle>()

    fun addLifeListener(listener:AbsLifecycle){
        if (!lifecycleListener.contains(listener)){
            lifecycleListener.add(listener)
        }
    }

    fun removeLifeListener(listener:AbsLifecycle){
        lifecycleListener.remove(listener)
    }

    fun makeLifeState(state: LifeState){
        currentLifeState = state
        lifecycleListener.forEach {
            it.onviewLifeStateChange(state)
        }
        when(state){
            LifeState.CREATE ->{
                dispatchCreateState()
            }
            LifeState.DESTROY ->{
                dispatchDestroyState()
            }
            LifeState.PAUSE ->{
                dispatchPauseState()
            }
            LifeState.START ->{
                dispatchStartState()
            }
            LifeState.STOP ->{
                dispatchStopState()
            }
            LifeState.RESUME ->{
                dispatchResumeState()
            }
        }
    }

    private fun dispatchResumeState() {
        lifecycleListener.forEach {
            it.onResume()
        }
    }

    private fun dispatchStopState() {
        lifecycleListener.forEach {
            it.onStop()
        }
    }

    private fun dispatchStartState() {
        lifecycleListener.forEach {
            it.onStart()
        }
    }

    private fun dispatchPauseState() {
        lifecycleListener.forEach {
            it.onPause()
        }
    }

    private fun dispatchDestroyState() {
        lifecycleListener.forEach {
            it.onDestroy()
        }
        lifecycleListener.clear()
    }

    private fun dispatchCreateState() {
        lifecycleListener.forEach {
            it.onCreate()
        }
    }

    fun isAtLeast(start: LifeState):Boolean {
        println("current state $currentLifeState === $start")
        val isAtLeastState:Boolean =currentLifeState.compareTo(start)>0
        println("isAtLeastState ==> $isAtLeastState")
        return isAtLeastState
    }

}