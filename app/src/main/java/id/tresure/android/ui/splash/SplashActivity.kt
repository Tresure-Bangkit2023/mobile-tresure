package id.tresure.android.ui.splash

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import id.tresure.android.MainActivity
import id.tresure.android.data.local.UserPreference
import id.tresure.android.databinding.ActivitySplashBinding
import id.tresure.android.helper.Helper.Companion.dataStore
import id.tresure.android.ui.ViewModelFactory
import id.tresure.android.ui.home.HomeFragment
import id.tresure.android.ui.home.HomeViewModel
import id.tresure.android.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SplashViewModel
    private var isLogin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        setupView()
        playAnimation()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(dataStore), application)
        )[SplashViewModel::class.java]

        viewModel.apply {
            getUser().observe(this@SplashActivity) { user ->
                this@SplashActivity.isLogin = user.token.isNotEmpty()
            }
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION") if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        supportActionBar?.hide()
    }

    private fun playAnimation() = AnimatorSet().apply {
        playSequentially(
            ObjectAnimator.ofFloat(binding.ivLogo, View.ALPHA, 1f).setDuration(2000)
        )
        start()
    }.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(a: Animator) {}

        override fun onAnimationEnd(a: Animator) {
            val intent = if (isLogin) {
                Intent(this@SplashActivity, MainActivity::class.java)
            } else {
                Intent(this@SplashActivity, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }

        override fun onAnimationCancel(a: Animator) {}

        override fun onAnimationRepeat(a: Animator) {}
    })
}