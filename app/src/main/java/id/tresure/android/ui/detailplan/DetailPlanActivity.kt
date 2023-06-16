package id.tresure.android.ui.detailplan

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.tresure.android.R
import id.tresure.android.data.local.UserPreference
import id.tresure.android.data.remote.response.PlanPlaceResponseItem
import id.tresure.android.databinding.ActivityDetailPlanBinding
import id.tresure.android.helper.Helper.Companion.dataStore
import id.tresure.android.ui.ViewModelFactory

class DetailPlanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailPlanBinding
    private lateinit var viewModel: DetailPlanViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        setupAction()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(dataStore), application)
        )[DetailPlanViewModel::class.java]

        viewModel.apply {
            isLoading.observe(this@DetailPlanActivity) { isLoading ->
                showLoading(isLoading)
            }

            snackBarText.observe(this@DetailPlanActivity) {
                it.getContentIfNotHandled()?.let { snackBarText ->
                    Snackbar.make(
                        window.decorView.rootView, snackBarText, Snackbar.LENGTH_SHORT
                    ).setBackgroundTint(
                        ContextCompat.getColor(
                            this@DetailPlanActivity, R.color.warningColor
                        )
                    ).setTextColor(
                        ContextCompat.getColor(
                            this@DetailPlanActivity, R.color.black
                        )
                    ).show()
                }
            }

            listPlanPlace.observe(this@DetailPlanActivity) { listPlanPlace ->
                setPlanPlaceList(listPlanPlace)
            }

            getUser().observe(this@DetailPlanActivity) { user ->
                viewModel.getPlanPlace("Bearer ${user.token}", user.userId)
            }
        }
    }

    private fun setPlanPlaceList(listPlanPlace: List<PlanPlaceResponseItem>?) {
        val adapter = DetailPlanAdapter(listPlanPlace)
        binding.rvDetailPlan.adapter = adapter
    }

    private fun setupAction() {
        val layoutManager = LinearLayoutManager(this)
        binding.apply {
            rvDetailPlan.layoutManager = layoutManager
            rvDetailPlan.setHasFixedSize(true)

            btnEditPlan.setOnClickListener {
                val intent = Intent(this@DetailPlanActivity, AddPlaceActivity::class.java)
                startActivity(
                    intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(this@DetailPlanActivity)
                        .toBundle()
                )
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}