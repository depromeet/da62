package com.da62.presenter.splash

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieDrawable
import com.da62.R
import com.da62.databinding.ActivitySplashBinding
import com.da62.presenter.main.MainActivity
import com.da62.presenter.base.BaseActivity
import com.da62.presenter.start.StartActivity
import org.jetbrains.anko.intentFor
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity() {

    private val viewModel by viewModel<SplashViewModel>()
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.lottie.apply {
            setAnimation("splash.json")
            repeatCount = LottieDrawable.INFINITE
            playAnimation()
        }
        viewModel.openToMain.observe(this, Observer {
            startActivity(intentFor<MainActivity>()).apply {
                finish()
            }
        })

        viewModel.openToStart.observe(this, Observer {
            startActivity(intentFor<StartActivity>()).apply {
                finish()
            }
        })
    }

}