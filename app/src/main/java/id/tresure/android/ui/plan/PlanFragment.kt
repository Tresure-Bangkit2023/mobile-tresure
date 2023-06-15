package id.tresure.android.ui.plan

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
import id.tresure.android.data.remote.response.PlanByUserIdResponseItem
import id.tresure.android.databinding.FragmentPlanBinding
import id.tresure.android.ui.ViewModelFactory
import id.tresure.android.ui.detailplan.DetailPlanActivity

class PlanFragment : Fragment() {

    private var _binding: FragmentPlanBinding? = null
    private val binding get() = _binding!!
    private val Context.dataStore by preferencesDataStore("UserPreference")
    private lateinit var viewModel: PlanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanBinding.inflate(inflater, container, false)
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
        viewModel = ViewModelProvider(activity, viewModelFactory)[PlanViewModel::class.java]
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

            listPlanByUserId.observe(viewLifecycleOwner) { listPlanByUserId ->
                setPlanByUserId(listPlanByUserId)
            }

            getUser().observe(viewLifecycleOwner) { user ->
                viewModel.getPlanByUserId("Bearer ${user.token}", user.userId)
            }
        }
    }

    private fun setPlanByUserId(listPlanByUserId: List<PlanByUserIdResponseItem>) {
        val adapter = PlanByUserIdAdapter(listPlanByUserId)
        adapter.setOnItemClickCallback(object : PlanByUserIdAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PlanByUserIdResponseItem) {
                activity.let {
                    val intent = Intent(it, DetailPlanActivity::class.java)
                    intent.putExtra(EXTRA_PLAN, data)
                    startActivity(intent)
                }

            }
        })
        binding.rvMyPlan.adapter = adapter
    }

    private fun setupAction() {
        val planByUserIdLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.apply {
            rvMyPlan.layoutManager = planByUserIdLayoutManager
            rvMyPlan.setHasFixedSize(true)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_PLAN = "plan"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}