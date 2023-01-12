package com.program.mvvmdemo

import android.text.TextUtils
import com.program.mvvmdemo.UserModel.Companion.STATE_LOGIN_FAILED
import com.program.mvvmdemo.UserModel.Companion.STATE_LOGIN_LOADING
import com.program.mvvmdemo.UserModel.Companion.STATE_LOGIN_SUCCESS
import com.program.mvvmdemo.lifecycle.AbsLifecycle
import com.program.mvvmdemo.lifecycle.ILifecycleOwner
import com.program.mvvmdemo.lifecycle.LifeState

class LoginPresenter(owner: ILifecycleOwner) :AbsLifecycle(){

    init {
        owner.getLifeProvider().addLifeListener(this)
    }

    private val userModel by lazy {
        UserModel()
    }

    fun checkUserNameState(account: String, callback: OnCheckUserNameStateResultCallback) {
        userModel.checkUserState(account) {
            when (it) {
                0 -> {
                    callback.onExist()
                }
                1 -> {
                    callback.onNotExist()
                }
            }
        }
    }

    interface OnCheckUserNameStateResultCallback {
        fun onNotExist()
        fun onExist()
    }

    fun doLogin(userName: String, password: String, callback: OnDoLoginStateChange) {

        if (TextUtils.isEmpty(userName)) {
            callback.onAccountFormatError()
            return
        }
        if (TextUtils.isEmpty(password)) {
            callback.onPasswordError()
            return
        }


        userModel.doLogin(userName, password) {
            when (it) {
                STATE_LOGIN_LOADING -> {
                    callback.onLoading()
                }

                STATE_LOGIN_SUCCESS -> {
                    callback.onLoadingSuccess()
                }

                STATE_LOGIN_FAILED -> {
                    callback.onLoadingFailed()
                }

            }
        }
    }

    interface OnDoLoginStateChange {
        fun onAccountFormatError()
        fun onPasswordError()

        fun onLoading()
        fun onLoadingSuccess()
        fun onLoadingFailed()
    }

    override fun onCreate() {

    }

    override fun onStart() {

    }

    override fun onResume() {

    }

    override fun onPause() {

    }

    override fun onStop() {

    }

    override fun onDestroy() {

    }

    override fun onviewLifeStateChange(state: LifeState) {
        "current state ==$state"
    }
}