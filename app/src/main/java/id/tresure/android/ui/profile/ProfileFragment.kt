package id.tresure.android.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import id.tresure.android.R
import id.tresure.android.data.local.UserPreference
import id.tresure.android.databinding.FragmentProfileBinding
import id.tresure.android.helper.Helper.Companion.dataStore
import id.tresure.android.ui.ViewModelFactory
import id.tresure.android.ui.home.HomeViewModel
import id.tresure.android.ui.login.LoginActivity
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val Context.dataStore by preferencesDataStore("UserPreference")
    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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
        viewModel = ViewModelProvider(activity, viewModelFactory)[ProfileViewModel::class.java]
    }

    private fun setupAction() {
        binding.tvLogout.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle(getString(R.string.keluar))
                setMessage("Yakin ingin keluar?")
                setPositiveButton("Ya") { _, _ ->
                    viewModel.logout()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                setNegativeButton("Tidak", null)
            }.show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}