package id.tresure.android.ui.detailplace

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.tresure.android.R
import id.tresure.android.data.remote.response.ArtResponseItem
import id.tresure.android.data.remote.response.PlacesResponseItem
import id.tresure.android.data.remote.response.ThemeParkResponseItem
import id.tresure.android.databinding.ActivityDetailPlaceBinding
import id.tresure.android.helper.Helper.Companion.currencyFormat
import id.tresure.android.ui.home.HomeFragment.Companion.EXTRA_ART
import id.tresure.android.ui.home.HomeFragment.Companion.EXTRA_PLACE
import id.tresure.android.ui.home.HomeFragment.Companion.EXTRA_THEME_PARK

class DetailPlaceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPlaceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        @Suppress("DEPRECATION")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (intent.getParcelableExtra(
                        EXTRA_THEME_PARK,
                        ThemeParkResponseItem::class.java
                    ) != null && intent.getParcelableExtra(
                        EXTRA_PLACE,
                        PlacesResponseItem::class.java
                    ) == null && intent.getParcelableExtra(
                        EXTRA_ART,
                        ArtResponseItem::class.java
                    ) == null
                ) {
                    binding.apply {
                        tvName.text =
                            intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK)?.name
                        tvDescription.text =
                            intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK)?.description
                        tvPrice.text = intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK)?.price?.currencyFormat()
                            ?: "Rp. 0"
                        tvCity.text =
                            intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK)?.city
                        tvRating.text =
                            intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK)?.rating.toString()
                        tvCategory.text =
                            when (intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK)?.categoryId) {
                                1 -> getString(R.string.bahari)
                                2 -> getString(R.string.budaya)
                                3 -> getString(R.string.cagar_alam)
                                4 -> getString(R.string.pusat_perbelanjaan)
                                5 -> getString(R.string.taman_hiburan)
                                6 -> getString(R.string.tempat_ibadah)
                                else -> getString(R.string.lainnya)
                            }
                    }
                    Glide.with(this)
                        .load(intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK)?.image)
                        .into(binding.ivPlace)
                } else if (intent.getParcelableExtra(
                        EXTRA_THEME_PARK,
                        ThemeParkResponseItem::class.java
                    ) == null && intent.getParcelableExtra(
                        EXTRA_PLACE,
                        PlacesResponseItem::class.java
                    ) != null && intent.getParcelableExtra(
                        EXTRA_ART,
                        ArtResponseItem::class.java
                    ) == null
                ) {
                    binding.apply {
                        tvName.text =
                            intent.getParcelableExtra<PlacesResponseItem>(EXTRA_PLACE)?.name
                        tvDescription.text =
                            intent.getParcelableExtra<PlacesResponseItem>(EXTRA_PLACE)?.description
                        tvPrice.text =
                            intent.getParcelableExtra<PlacesResponseItem>(EXTRA_PLACE)?.price?.currencyFormat()
                                ?: "Rp. 0"
                        tvCity.text =
                            intent.getParcelableExtra<PlacesResponseItem>(EXTRA_PLACE)?.city
                        tvRating.text =
                            intent.getParcelableExtra<PlacesResponseItem>(EXTRA_PLACE)?.rating.toString()
                        tvCategory.text =
                            when (intent.getParcelableExtra<PlacesResponseItem>(EXTRA_PLACE)?.categoryId) {
                                1 -> getString(R.string.bahari)
                                2 -> getString(R.string.budaya)
                                3 -> getString(R.string.cagar_alam)
                                4 -> getString(R.string.pusat_perbelanjaan)
                                5 -> getString(R.string.taman_hiburan)
                                6 -> getString(R.string.tempat_ibadah)
                                else -> getString(R.string.lainnya)
                            }
                    }
                    Glide.with(this)
                        .load(intent.getParcelableExtra<PlacesResponseItem>(EXTRA_PLACE)?.image)
                        .into(binding.ivPlace)
                } else if (intent.getParcelableExtra(
                        EXTRA_THEME_PARK,
                        ThemeParkResponseItem::class.java
                    ) == null && intent.getParcelableExtra(
                        EXTRA_PLACE,
                        PlacesResponseItem::class.java
                    ) == null && intent.getParcelableExtra(
                        EXTRA_ART,
                        ArtResponseItem::class.java
                    ) != null
                ) {
                    binding.apply {
                        tvName.text = intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART)?.name
                        tvDescription.text =
                            intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART)?.description
                        tvPrice.text =
                            intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART)?.price?.currencyFormat() ?: "Rp. 0"
                        tvCity.text = intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART)?.city
                        tvRating.text =
                            intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART)?.rating.toString()
                        tvCategory.text =
                            when (intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART)?.categoryId) {
                                1 -> getString(R.string.bahari)
                                2 -> getString(R.string.budaya)
                                3 -> getString(R.string.cagar_alam)
                                4 -> getString(R.string.pusat_perbelanjaan)
                                5 -> getString(R.string.taman_hiburan)
                                6 -> getString(R.string.tempat_ibadah)
                                else -> getString(R.string.lainnya)
                            }
                    }
                    Glide.with(this)
                        .load(intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART)?.image)
                        .into(binding.ivPlace)
                } else {
                    null
                }
            } else {
                if (intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK) != null && intent.getParcelableExtra<PlacesResponseItem>(
                        EXTRA_PLACE
                    ) == null && intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART) == null
                ) {
                    binding.apply {
                        tvName.text =
                            intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK)?.name
                        tvDescription.text =
                            intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK)?.description
                        tvPrice.text =
                            intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK)?.price?.currencyFormat() ?: "Rp. 0"
                        tvCity.text =
                            intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK)?.city
                        tvRating.text =
                            intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK)?.rating.toString()
                        tvCategory.text =
                            when (intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK)?.categoryId) {
                                1 -> getString(R.string.bahari)
                                2 -> getString(R.string.budaya)
                                3 -> getString(R.string.cagar_alam)
                                4 -> getString(R.string.pusat_perbelanjaan)
                                5 -> getString(R.string.taman_hiburan)
                                6 -> getString(R.string.tempat_ibadah)
                                else -> getString(R.string.lainnya)
                            }
                    }
                    Glide.with(this)
                        .load(intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK)?.image)
                        .into(binding.ivPlace)
                } else if (intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK) == null && intent.getParcelableExtra<PlacesResponseItem>(
                        EXTRA_PLACE
                    ) != null && intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART) == null
                ) {
                    binding.apply {
                        tvName.text =
                            intent.getParcelableExtra<PlacesResponseItem>(EXTRA_PLACE)?.name
                        tvDescription.text =
                            intent.getParcelableExtra<PlacesResponseItem>(EXTRA_PLACE)?.description
                        tvPrice.text =
                            intent.getParcelableExtra<PlacesResponseItem>(EXTRA_PLACE)?.price?.currencyFormat()
                                ?: "Rp. 0"
                        tvCity.text =
                            intent.getParcelableExtra<PlacesResponseItem>(EXTRA_PLACE)?.city
                        tvRating.text =
                            intent.getParcelableExtra<PlacesResponseItem>(EXTRA_PLACE)?.rating.toString()
                        tvCategory.text =
                            when (intent.getParcelableExtra<PlacesResponseItem>(EXTRA_PLACE)?.categoryId) {
                                1 -> getString(R.string.bahari)
                                2 -> getString(R.string.budaya)
                                3 -> getString(R.string.cagar_alam)
                                4 -> getString(R.string.pusat_perbelanjaan)
                                5 -> getString(R.string.taman_hiburan)
                                6 -> getString(R.string.tempat_ibadah)
                                else -> getString(R.string.lainnya)
                            }
                    }
                    Glide.with(this)
                        .load(intent.getParcelableExtra<PlacesResponseItem>(EXTRA_PLACE)?.image)
                        .into(binding.ivPlace)
                } else if (intent.getParcelableExtra<ThemeParkResponseItem>(EXTRA_THEME_PARK) == null && intent.getParcelableExtra<PlacesResponseItem>(
                        EXTRA_PLACE
                    ) == null && intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART) != null
                ) {
                    binding.apply {
                        tvName.text = intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART)?.name
                        tvDescription.text =
                            intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART)?.description
                        tvPrice.text =
                            intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART)?.price?.currencyFormat() ?: "Rp. 0"
                        tvCity.text = intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART)?.city
                        tvRating.text =
                            intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART)?.rating.toString()
                        tvCategory.text =
                            when (intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART)?.categoryId) {
                                1 -> getString(R.string.bahari)
                                2 -> getString(R.string.budaya)
                                3 -> getString(R.string.cagar_alam)
                                4 -> getString(R.string.pusat_perbelanjaan)
                                5 -> getString(R.string.taman_hiburan)
                                6 -> getString(R.string.tempat_ibadah)
                                else -> getString(R.string.lainnya)
                            }
                    }
                    Glide.with(this)
                        .load(intent.getParcelableExtra<ArtResponseItem>(EXTRA_ART)?.image)
                        .into(binding.ivPlace)
                } else {
                    null
                }
            }
    }
}