package id.tresure.android.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import id.tresure.android.R
import id.tresure.android.data.local.UserPreference
import id.tresure.android.databinding.ActivityRegisterBinding
import id.tresure.android.helper.Helper.Companion.dataStore
import id.tresure.android.helper.Helper.Companion.isValidEmail
import id.tresure.android.helper.Helper.Companion.isValidPassword
import id.tresure.android.ui.ViewModelFactory
import id.tresure.android.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        setupAction()
        setButton()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(dataStore), application)
        )[RegisterViewModel::class.java]

        viewModel.apply {
            snackBarText.observe(this@RegisterActivity) {
                it.getContentIfNotHandled()?.let { snackBarText ->
                    Snackbar.make(window.decorView.rootView, snackBarText, Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(
                                this@RegisterActivity, R.color.warningColor
                            )
                        ).setTextColor(ContextCompat.getColor(this@RegisterActivity, R.color.black))
                        .show()
                }
            }
            isLoading.observe(this@RegisterActivity) { isLoading ->
                showLoading(isLoading)
            }
            isUserCreated.observe(this@RegisterActivity) {
                if (it == true) {
                    AlertDialog.Builder(this@RegisterActivity).setTitle("Success")
                        .setMessage("User created successfully")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish()
                        }.show()
                }
            }
        }
    }

    private fun setButton() {
        val fullname = binding.etFullname.text.toString()
        val username = binding.etUsername.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val confirmPassword = binding.etConfirmPassword.text.toString()

        binding.btnRegister.isEnabled =
            true && username.isNotEmpty() && true && fullname.isNotEmpty() && true && email.isNotEmpty() && email.isValidEmail() && true && password.isNotEmpty() && password.isValidPassword() && true && confirmPassword.isNotEmpty() && confirmPassword == password
    }

    private fun setupAction() {
        binding.apply {
            etFullname.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setButton()
                }

                override fun afterTextChanged(s: Editable?) {
                    etFullname.error =
                        if (etFullname.text.toString().isEmpty()) "Tidak boleh kosong"
                        else null
                }
            })

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
                        if (etUsername.text.toString().isEmpty()) "Tidak boleh kosong"
                        else null
                }
            })

            etEmail.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setButton()
                }

                override fun afterTextChanged(s: Editable?) {
                    etEmail.error = if (etEmail.text.toString().isEmpty()) "Tidak boleh kosong"
                    else if (!s.isValidEmail()) "Email tidak valid"
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
                    etPassword.error =
                        if (!etPassword.text.toString().isValidPassword()) "Minimal 8 karakter"
                        else if (etPassword.text.toString().isEmpty()) "Tidak boleh kosong"
                        else null
                }
            })

            etConfirmPassword.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                    etConfirmPassword.error =
                        if (etConfirmPassword.text.toString() != etConfirmPassword.text.toString()) "Password tidak cocok"
                        else null
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setButton()
                }

                override fun afterTextChanged(s: Editable?) {
                    etConfirmPassword.error =
                        if (etConfirmPassword.text.toString().isEmpty()) "Tidak boleh kosong"
                        else if (etPassword.text.toString() != etConfirmPassword.text.toString()) "Password tidak cocok"
                        else null
                }
            })

            binding.apply {
                btnRegister.setOnClickListener {
                    val email = etEmail.text.toString()
                    val fullname = etFullname.text.toString()
                    val username = etUsername.text.toString()
                    val password = etPassword.text.toString()
                    viewModel.register(username, password, email, fullname)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.ivLogo.visibility = if (isLoading) View.GONE else View.VISIBLE
    }
}