package id.tresure.android.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userId: Int, val username: String, val token: String
) : Parcelable