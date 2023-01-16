package com.program.mvvmdemo.taobao

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.program.mvvmdemo.R

class OnSellActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onsell)
        val viewModel:OnSellViewModel = ViewModelProvider(this).get(OnSellViewModel::class.java)
        viewModel.loadContent()
    }

}