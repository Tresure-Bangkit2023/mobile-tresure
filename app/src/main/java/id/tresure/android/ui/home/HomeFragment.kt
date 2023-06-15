package id.tresure.android.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import id.tresure.android.R
import id.tresure.android.data.local.UserPreference
import id.tresure.android.data.remote.response.ArtResponseItem
import id.tresure.android.data.remote.response.PlacesResponseItem
import id.tresure.android.data.remote.response.PlanResponseItem
import id.tresure.android.data.remote.response.ThemeParkResponseItem
import id.tresure.android.databinding.FragmentHomeBinding
import id.tresure.android.ui.ViewModelFactory
import id.tresure.android.ui.detailplace.DetailPlaceActivity
import id.tresure.android.ui.detailplan.DetailPlanActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val Context.dataStore by preferencesDataStore("UserPreference")
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        setupAction()
    }

    private fun initViewModel() {
        val activity = requireActivity()
        val viewModelFactory =
            ViewModelFactory(UserPreference.getInstance(activity.dataStore), activity.application)
        viewModel = ViewModelProvider(activity, viewModelFactory)[HomeViewModel::class.java]
        viewModel.apply {
            isLoading.observe(viewLifecycleOwner) { isLoading ->
                showLoading(isLoading)
            }

            snackBarText.observe(viewLifecycleOwner) {
                it.getContentIfNotHandled()?.let { snackBarText ->
                    Snackbar.make(
                        requireActivity().window.decorView.rootView,
                        snackBarText,
                        Snackbar.LENGTH_SHORT
                    ).setBackgroundTint(
                        ContextCompat.getColor(
                            requireContext(), R.color.warningColor
                        )
                    ).setTextColor(
                        ContextCompat.getColor(
                            requireContext(), R.color.white
                        )
                    ).show()
                }
            }

            listPlan.observe(viewLifecycleOwner) { listPlan ->
                setPlanList(listPlan)
            }
            listPlace.observe(viewLifecycleOwner) { listPlace ->
                setPlaceList(listPlace)
            }
            listArt.observe(viewLifecycleOwner) { listArt ->
                setArtList(listArt)
            }
            listThemePark.observe(viewLifecycleOwner) { listThemePark ->
                setThemeParkList(listThemePark)
            }

            getUser().observe(viewLifecycleOwner) { user ->
                viewModel.getAllPlans("Bearer ${user.token}")
                viewModel.getAllPlace("Bearer ${user.token}")
                viewModel.getArt("Bearer ${user.token}")
                viewModel.getThemePark("Bearer ${user.token}")
            }
        }
    }

    private fun setPlanList(listPlan: List<PlanResponseItem>) {
        val adapter = PlanAdapter(listPlan)
        adapter.setOnItemClickCallback(object : PlanAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PlanResponseItem) {
                activity.let {
                    val intent = Intent(it, DetailPlanActivity::class.java)
                    intent.putExtra(EXTRA_PLAN, data)
                    startActivity(intent)
                }

            }
        })
        binding.rvOtherPlan.adapter = adapter
    }

    private fun setPlaceList(listPlace: List<PlacesResponseItem>) {
        val adapter = PlaceAdapter(listPlace)
        adapter.setOnItemClickCallback(object : PlaceAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PlacesResponseItem) {
                activity.let {
                    val intent = Intent(it, DetailPlaceActivity::class.java)
                    intent.putExtra(EXTRA_PLACE, data)
                    startActivity(intent)
                }

            }
        })
        binding.rvViral.adapter = adapter
    }

    private fun setArtList(listArt: List<ArtResponseItem>) {
        val adapter = ArtAdapter(listArt)
        adapter.setOnItemClickCallback(object : ArtAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ArtResponseItem) {
                activity.let {
                    val intent = Intent(it, DetailPlaceActivity::class.java)
                    intent.putExtra(EXTRA_ART, data)
                    startActivity(intent)
                }

            }
        })
        binding.rvArt.adapter = adapter
    }

    private fun setThemeParkList(listThemePark: List<ThemeParkResponseItem>) {
        val adapter = ThemeParkAdapter(listThemePark)
        adapter.setOnItemClickCallback(object : ThemeParkAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ThemeParkResponseItem) {
                activity.let {
                    val intent = Intent(it, DetailPlaceActivity::class.java)
                    intent.putExtra(EXTRA_THEME_PARK, data)
                    startActivity(intent)
                }

            }
        })
        binding.rvFamilyPlace.adapter = adapter
    }

    private fun setupAction() {
        val planLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val placeLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val artLayoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        val themeParkLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.apply {
            rvOtherPlan.layoutManager = planLayoutManager
            rvOtherPlan.setHasFixedSize(true)
            rvViral.layoutManager = placeLayoutManager
            rvViral.setHasFixedSize(true)
            rvArt.layoutManager = artLayoutManager
            rvArt.setHasFixedSize(true)
            rvFamilyPlace.layoutManager = themeParkLayoutManager
            rvFamilyPlace.setHasFixedSize(true)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_PLAN = "plan"
        const val EXTRA_PLACE = "place"
        const val EXTRA_ART = "art"
        const val EXTRA_THEME_PARK = "theme_park"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}