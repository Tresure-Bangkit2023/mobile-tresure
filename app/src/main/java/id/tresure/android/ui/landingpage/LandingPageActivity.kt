package id.tresure.android.ui.landingpage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.tresure.android.databinding.ActivityLandingPageBinding
import id.tresure.android.ui.login.LoginActivity
import id.tresure.android.ui.register.RegisterActivity

class LandingPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                startActivity(Intent(this@LandingPageActivity, LoginActivity::class.java))
                finish()
            }

            btnRegister.setOnClickListener {
                startActivity(Intent(this@LandingPageActivity, RegisterActivity::class.java))
                finish()
            }
        }
    }

}