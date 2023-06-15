package id.tresure.android.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import id.tresure.android.R
import id.tresure.android.data.local.UserPreference
import id.tresure.android.databinding.FragmentProfileBinding
import id.tresure.android.ui.ViewModelFactory
import id.tresure.android.ui.login.LoginActivity

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

        viewModel.apply {
            getUser().observe(viewLifecycleOwner) {
                binding.tvUsername.text = it.username
            }
        }
    }

    private fun setupAction() {
        binding.tvLogout.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle(getString(R.string.keluar))
                setMessage(getString(R.string.yakin_ingin_keluar))
                setPositiveButton("Ya") { _, _ ->
                    viewModel.logout()
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
                setNegativeButton(getString(R.string.tidak), null)
            }.show()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}