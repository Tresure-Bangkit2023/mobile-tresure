package id.tresure.android.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.tresure.android.data.local.UserPreference
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref: UserPreference) : ViewModel() {

    fun logout() {
        viewModelScope.launch {
            pref.deleteUser()
        }
    }
}