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
import id.tresure.android.data.remote.response.DataItem
import id.tresure.android.data.remote.response.PlacesResponseItem
import id.tresure.android.data.remote.response.PlanPlaceResponseItem
import id.tresure.android.databinding.ActivityAddPlaceBinding
import id.tresure.android.databinding.ActivityDetailPlanBinding
import id.tresure.android.helper.Helper.Companion.dataStore
import id.tresure.android.ui.ViewModelFactory
import id.tresure.android.ui.createplan.CreatePlanActivity
import id.tresure.android.ui.detailplace.DetailPlaceActivity
import id.tresure.android.ui.home.HomeFragment
import id.tresure.android.ui.home.PlaceAdapter
import id.tresure.android.ui.plan.PlanFragment

class AddPlaceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPlaceBinding
    private lateinit var viewModel: AddPlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        setupAction()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreference.getInstance(dataStore), application)
        )[AddPlaceViewModel::class.java]

        viewModel.apply {
            isLoading.observe(this@AddPlaceActivity) { isLoading ->
                showLoading(isLoading)
            }

            snackBarText.observe(this@AddPlaceActivity) {
                it.getContentIfNotHandled()?.let { snackBarText ->
                    Snackbar.make(
                        window.decorView.rootView, snackBarText, Snackbar.LENGTH_SHORT
                    ).setBackgroundTint(
                        ContextCompat.getColor(
                            this@AddPlaceActivity, R.color.warningColor
                        )
                    ).setTextColor(
                        ContextCompat.getColor(
                            this@AddPlaceActivity, R.color.black
                        )
                    ).show()
                }
            }

            listPlanRecommendationByCity.observe(this@AddPlaceActivity) { listPlanRecommendationByCity ->
                listPlanRecommendationByCity?.let { setPlanRecommendationByCity(it) }
            }

            getUser().observe(this@AddPlaceActivity) { user ->
                viewModel.getPlanRecommendationByCity("Bearer ${user.token}", user.username, "Jakarta")
            }
        }
    }

    private fun setPlanRecommendationByCity(listPlanRecommendationByCity: List<DataItem>) {
        val adapter = AddPlaceAdapter(listPlanRecommendationByCity)
        adapter.setOnItemClickCallback(object : AddPlaceAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataItem) {

            }
        })
        binding.rvPlanRecommendationByCity.adapter = adapter
    }

    private fun setupAction() {
        val layoutManager = LinearLayoutManager(this)
        binding.apply {
            rvPlanRecommendationByCity.layoutManager = layoutManager
            rvPlanRecommendationByCity.setHasFixedSize(true)

        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}