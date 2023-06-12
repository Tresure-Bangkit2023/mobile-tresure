package id.tresure.android.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String, val token: String
) : Parcelable