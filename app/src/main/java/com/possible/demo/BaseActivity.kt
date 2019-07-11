package com.possible.demo

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import android.arch.lifecycle.ViewModel
import android.support.annotation.Nullable


abstract class BaseActivity: DaggerAppCompatActivity() {

    private var viewModel: MainViewModel? = null

    /**
     *
     * @return view model instance
     */
    abstract fun getViewModel(): MainViewModel

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.viewModel = if (viewModel == null) getViewModel() else viewModel
    }
}