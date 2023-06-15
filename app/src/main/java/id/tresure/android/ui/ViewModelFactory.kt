package id.tresure.android.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.tresure.android.data.local.UserPreference
import id.tresure.android.ui.createplan.CreatePlanViewModel
import id.tresure.android.ui.home.HomeViewModel
import id.tresure.android.ui.login.LoginViewModel
import id.tresure.android.ui.plan.PlanViewModel
import id.tresure.android.ui.profile.ProfileViewModel
import id.tresure.android.ui.register.RegisterViewModel
import id.tresure.android.ui.splash.SplashViewModel

class ViewModelFactory(
    private val pref: UserPreference, private val application: Application
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                SplashViewModel(pref) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(application) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref, application) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(pref, application) as T
            }

            modelClass.isAssignableFrom(CreatePlanViewModel::class.java) -> {
                CreatePlanViewModel(pref, application) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(pref) as T
            }

            modelClass.isAssignableFrom(PlanViewModel::class.java) -> {
                PlanViewModel(pref, application) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}