package id.tresure.android.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import id.tresure.android.MainActivity
import id.tresure.android.R
import id.tresure.android.data.local.UserPreference
import id.tresure.android.databinding.ActivityLoginBinding
import id.tresure.android.helper.Helper.Companion.dataStore
import id.tresure.android.helper.Helper.Companion.isValidPassword
import id.tresure.android.ui.ViewModelFactory
import id.tresure.android.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        setupAction()
        setButton()

    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(dataStore), application)
        )[LoginViewModel::class.java]

        viewModel.apply {
            snackBarText.observe(this@LoginActivity) {
                it.getContentIfNotHandled()?.let { snackBarText ->
                    Snackbar.make(window.decorView.rootView, snackBarText, Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(
                                this@LoginActivity, R.color.warningColor
                            )
                        ).setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.black))
                        .show()
                }
            }

            loginError.observe(this@LoginActivity) { loginError ->

                if (!loginError) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun setupAction() {
        binding.apply {
            etUsername.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setButton()
                }

                override fun afterTextChanged(s: Editable?) {
                    etUsername.error =
                        if (etUsername.text.toString().isEmpty()) "Username tidak boleh kosong"
                        else null
                }
            })

            etPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setButton()
                }

                override fun afterTextChanged(s: Editable?) {
                }
            })

            btnLogin.setOnClickListener {
                binding.let {
                    val username = it.etUsername.text.toString()
                    val password = it.etPassword.text.toString()
                    viewModel.login(username, password)
                }
            }

            tvDaftar.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this@LoginActivity, btnLogin, "btnLogin"
                )
                startActivity(intent, options.toBundle())
            }
        }
    }

    private fun setButton() {
        val username = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        binding.btnLogin.isEnabled =
            true && username.isNotEmpty() && true && password.isNotEmpty() && password.isValidPassword()
    }
}