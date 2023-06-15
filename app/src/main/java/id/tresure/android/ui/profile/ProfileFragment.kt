package id.tresure.android.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import id.tresure.android.data.local.UserPreference
import id.tresure.android.databinding.FragmentProfileBinding
import id.tresure.android.helper.Helper.Companion.dataStore
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    private val binding get() = _binding!!
    private lateinit var userPreference: UserPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val dataStore: DataStore<Preferences> = requireContext().dataStore

        userPreference = UserPreference.getInstance(dataStore)

        viewLifecycleOwner.lifecycleScope.launch {
            userPreference.getUser().collect { user ->
                val username = user.username
                binding.tvUsername.text = username
            }
        }

        return root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}