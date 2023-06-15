package id.tresure.android.ui.createplan

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import id.tresure.android.R
import id.tresure.android.data.local.UserPreference
import id.tresure.android.databinding.ActivityCreatePlanBinding
import id.tresure.android.helper.Helper.Companion.dataStore
import id.tresure.android.ui.ViewModelFactory
import id.tresure.android.ui.detailplan.DetailPlanActivity

class CreatePlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreatePlanBinding
    private lateinit var viewModel: CreatePlanViewModel
    private var isCitySelected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cities = arrayOf(
            getString(R.string.jakarta),
            getString(R.string.surabaya),
            getString(R.string.bandung),
            getString(R.string.semarang),
            getString(R.string.yogyakarta),
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        binding.spCity.adapter = adapter

        initViewModel()
        setupAction()
        setButton()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(dataStore), application)
        )[CreatePlanViewModel::class.java]

        viewModel.apply {
            snackBarText.observe(this@CreatePlanActivity) {
                it.getContentIfNotHandled()?.let { snackBarText ->
                    Snackbar.make(window.decorView.rootView, snackBarText, Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(
                            ContextCompat.getColor(
                                this@CreatePlanActivity, R.color.warningColor
                            )
                        ).setTextColor(
                            ContextCompat.getColor(
                                this@CreatePlanActivity,
                                R.color.white
                            )
                        ).show()
                }
            }
            isLoading.observe(this@CreatePlanActivity) { isLoading ->
                showLoading(isLoading)
            }
            isPlanCreated.observe(this@CreatePlanActivity) {
                if (it == true) {
                    AlertDialog.Builder(this@CreatePlanActivity)
                        .setTitle(getString(R.string.berhasil))
                        .setMessage(getString(R.string.rencana_berhasil_dibuat))
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()
                            startActivity(
                                Intent(
                                    this@CreatePlanActivity,
                                    DetailPlanActivity::class.java
                                )
                            )
                            finish()
                        }.show()
                }
            }
        }
    }

    private fun setButton() {
        val title = binding.etTitle.text.toString()
        val person = binding.etPerson.text.toString()
        val city = binding.spCity.selectedItem.toString()
        val startDestination = binding.etStartDestination.text.toString()
        val startTime = binding.etStartTime.text.toString()
        val budget = binding.etBudget.text.toString()

        binding.btnSavePlan.isEnabled =
            true && title.isNotEmpty() && true && person.isNotEmpty() && true && city.isNotEmpty() && startDestination.isNotEmpty() && true && startTime.isNotEmpty() && budget.isNotEmpty()
    }

    private fun setupAction() {
        binding.apply {
            etTitle.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setButton()
                }

                override fun afterTextChanged(s: Editable?) {
                    etTitle.error = if (etTitle.text.toString()
                            .isEmpty()
                    ) getString(R.string.tidak_boleh_kosong)
                    else null
                }
            })

            etPerson.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setButton()
                }

                override fun afterTextChanged(s: Editable?) {
                    etPerson.error = if (etPerson.text.toString()
                            .isEmpty()
                    ) getString(R.string.tidak_boleh_kosong)
                    else null
                }
            })

            spCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    setButton()
                    val selectedCity = parent?.getItemAtPosition(position)?.toString() ?: ""
                    isCitySelected = !selectedCity.isNullOrEmpty()
                    // Lakukan operasi lain yang diperlukan dengan selectedCity
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    isCitySelected = false
                    getString(R.string.tidak_boleh_kosong)
                }
            }

            etStartDestination.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setButton()
                }

                override fun afterTextChanged(s: Editable?) {
                    etStartDestination.error = if (etStartDestination.text.toString()
                            .isEmpty()
                    ) getString(R.string.tidak_boleh_kosong)
                    else null
                }
            })

            etStartTime.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setButton()
                }

                override fun afterTextChanged(s: Editable?) {
                    etStartTime.error = if (etStartTime.text.toString()
                            .isEmpty()
                    ) getString(R.string.tidak_boleh_kosong)
                    else null
                }
            })

            etBudget.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    setButton()
                }

                override fun afterTextChanged(s: Editable?) {
                    etBudget.error = if (etBudget.text.toString()
                            .isEmpty()
                    ) getString(R.string.tidak_boleh_kosong)
                    else null
                }
            })

        }
        binding.apply {
            btnSavePlan.setOnClickListener {
                val intent = Intent(this@CreatePlanActivity, DetailPlanActivity::class.java)
                startActivity(
                    intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this@CreatePlanActivity)
                        .toBundle()
                )
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}