package id.tresure.android.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
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
                        ).setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
                        .show()
                }
            }

            isLoading.observe(this@LoginActivity) { isLoading ->
                showLoading(isLoading)
            }

            loginError.observe(this@LoginActivity) { loginError ->
                showLoginInvalid(loginError)

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
                    showLoginInvalid(false)
                }

                override fun afterTextChanged(s: Editable?) {
                    etUsername.error = if (etUsername.text.toString()
                            .isEmpty()
                    ) getString(R.string.tidak_boleh_kosong)
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
                    showLoginInvalid(false)
                }

                override fun afterTextChanged(s: Editable?) {
                    etPassword.error = if (!etPassword.text.toString().isValidPassword()) getString(
                        R.string.minimal_8_karakter
                    )
                    else if (etPassword.text.toString()
                            .isEmpty()
                    ) getString(R.string.tidak_boleh_kosong)
                    else null
                }
            })

            btnLogin.setOnClickListener {
                binding.let {
                    val username = it.etUsername.text.toString()
                    val password = it.etPassword.text.toString()
                    viewModel.login(username, password)
                }
                it.hideKeyboard()
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

    private fun showLoginInvalid(isError: Boolean) {
        binding.cvLoginInvalid.visibility = if (isError) View.VISIBLE else View.GONE
    }

    private fun View.hideKeyboard() {
        val inputManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.ivLogo.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}