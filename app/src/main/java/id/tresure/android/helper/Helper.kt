package id.tresure.android.helper

import android.content.Context
import android.util.Patterns
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class Helper {
    companion object {
        fun String.isValidPassword() = !isNullOrEmpty() && this.count() >= 8
        fun CharSequence?.isValidEmail() =
            !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

        fun Number.currencyFormat(): String {
            val currency = DecimalFormat.getCurrencyInstance() as DecimalFormat
            val rupiah = DecimalFormatSymbols()
            rupiah.currencySymbol = "IDR "
            rupiah.monetaryDecimalSeparator = ','
            rupiah.groupingSeparator = '.'
            currency.decimalFormatSymbols = rupiah

            return currency.format(this)
        }

        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_pref")

    }
}