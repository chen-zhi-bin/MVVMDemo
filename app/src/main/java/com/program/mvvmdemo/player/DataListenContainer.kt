package com.program.mvvmdemo.player

import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.program.mvvmdemo.App

/**
 * 数据容器，可以监听数据变化
 */
class DataListenContainer<T> {

    private val valuesObservers = arrayListOf<(T?) -> Unit>()
    private val viewLifecycleProvider = hashMapOf<(T?) -> Unit, Lifecycle>()

    var value: T? = null
        //数据变化的时候，通知更新
        set(value: T?) {
            //判断当前线程是不是主线程
            //如果是，则直接执行，否则切换到主线程再执行
            if (Looper.getMainLooper().thread === Thread.currentThread()) {
                //判断对应view的生命周期
                field = value   //没有这个数据就不会记录
                valuesObservers.forEach {
                    dispatchValue(it, value)
                }
            } else {
                App.handler.post {
                    field = value   //没有这个数据就不会记录
                    valuesObservers.forEach {
                        dispatchValue(it, value)
                    }
                }
            }

        }

    private fun dispatchValue(it: (T?) -> Unit, value: T?) {
        val lifecycle: Lifecycle? = viewLifecycleProvider[it]
        if (lifecycle != null &&
            lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)
        ) {//至少时启动
            println("更新ui")
            it.invoke(value)
        }else{
            println("UI不可见，不进行更新")
        }
    }


    /**
     * 有可能有多个view进行监听
     * //所以owner - block
     * 要管理起来
     */
    fun addListener(owner: LifecycleOwner, valueObserver: (T?) -> Unit) {
//        viewLifecycleProvider.put(block,owner.getLifeProvider())
        val lifecycle: Lifecycle = owner.lifecycle
        viewLifecycleProvider[valueObserver] = lifecycle
        //当view destroy的时候，要从集合中删除
        val observerWrapper = ValueObserverWrapper(valueObserver)
        lifecycle.addObserver(observerWrapper)

        if (!valuesObservers.contains(valueObserver)) {
            valuesObservers.add(valueObserver)
        }
    }

    inner class ValueObserverWrapper(private val valueObserver: (T?) -> Unit) : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun removeValueObserver() {
            println("removeValueObserver")
            //当监听到当前view，生命周期为Destroy的时候，就把LifecycleProvider从集合中删除
            viewLifecycleProvider.remove(valueObserver)
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop(owner:LifecycleOwner){
            println("stop owner--> $owner")
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
        fun onAny(owner:LifecycleOwner,event: Lifecycle.Event){
            println("Event--> $event")
            println("owner--> $owner")
        }
    }
}