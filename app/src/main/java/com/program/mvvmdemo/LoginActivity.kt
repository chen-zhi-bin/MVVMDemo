package com.program.mvvmdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.program.mvvmdemo.base.BaseActivity
import com.program.mvvmdemo.musicsList.MusicPresenter
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : BaseActivity(),
    LoginPresenter.OnCheckUserNameStateResultCallback, LoginPresenter.OnDoLoginStateChange {

    private val musicPresenter by lazy {
        MusicPresenter(this)
    }

    private val loginController by lazy {
        LoginPresenter(this)
    }


    private var isUserNameCanBeUser = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initListener()
    }

    private fun initListener() {
        loginBtn.setOnClickListener {
            Toast.makeText(this,"asd",Toast.LENGTH_SHORT).show()
            toLogin()
        }

        accountInputBox.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                loginController.checkUserNameState(p0.toString(),this@LoginActivity)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    /**
     * 处理登录逻辑
     */
    private fun toLogin() {

        var account: String = accountInputBox.text.toString()
        var password: String = passwordInputBox.text.toString()
        if (!isUserNameCanBeUser){
            //已存在提示一下
        }
            loginController.doLogin(account,password,this)
        }

    override fun onNotExist() {
        //用户名可用
        loginTipsText?.text="该用户名可以使用"
    }

    override fun onExist() {
        this.isUserNameCanBeUser = false
        loginTipsText?.text="该用户名已经存在"
    }

    override fun onAccountFormatError() {
        loginTipsText?.text="账号不能为空"
    }

    override fun onPasswordError() {
        loginTipsText?.text="密码不能为空"
    }

    override fun onLoading() {
        loginTipsText?.text = "登录中"

    }

    override fun onLoadingSuccess() {
        loginTipsText?.text = "登录成功...."
    }

    override fun onLoadingFailed() {
        loginTipsText?.text = "登录失败。"

    }
}