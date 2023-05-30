package id.tresure.android.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.tresure.android.data.remote.api.ApiConfig
import id.tresure.android.data.remote.response.PlaceResponse
import id.tresure.android.data.remote.response.ResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _listPlace = MutableLiveData<List<ResponseItem>>()
    val listPlace: LiveData<List<ResponseItem>> = _listPlace

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "HomeViewModel"
    }

    fun getAllPlaces() {
        showLoading(true)
        val client = ApiConfig.getApiService().getAllPlaces()
        client.enqueue(object : Callback<List<PlaceResponse>> {
            override fun onResponse(
                call: Call<List<PlaceResponse>>, response: Response<List<PlaceResponse>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listPlace.value = responseBody as List<ResponseItem>
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<PlaceResponse>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun showLoading(state: Boolean) {
        _isLoading.value = state
    }
}