package id.tresure.android.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<User> {
        return dataStore.data.map { preferences ->
            User(
                preferences[USERID_KEY] ?: 0,
                preferences[USERNAME_KEY] ?: "",
                preferences[TOKEN_KEY] ?: ""
            )
        }
    }

    suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[USERID_KEY] = user.userId
            preferences[USERNAME_KEY] = user.username
            preferences[TOKEN_KEY] = user.token
        }
    }

    suspend fun deleteUser() {
        dataStore.edit { preferences ->
            preferences[USERID_KEY] ?: 0
            preferences[USERNAME_KEY] = ""
            preferences[TOKEN_KEY] = ""
        }
    }

    fun getPlan(): Flow<Plan> {
        return dataStore.data.map { preferences ->
            Plan(
                preferences[PLANID_KEY] ?: "",
                preferences[USERID_KEY] ?: 0,
                preferences[TITLE_KEY] ?: "",
                preferences[NUMOFPEOPLE_KEY] ?: 0,
                preferences[CITY_KEY] ?: "",
                preferences[STARTLOCATION_KEY] ?: "",
                preferences[STARTTIME_KEY] ?: ""
            )
        }
    }

    suspend fun savePlan(plan: Plan) {
        dataStore.edit { preferences ->
            preferences[PLANID_KEY] = plan.planId
            preferences[USERID_KEY] = plan.userId
            preferences[TITLE_KEY] = plan.title
            preferences[NUMOFPEOPLE_KEY] = plan.numOfPeople
            preferences[CITY_KEY] = plan.city
            preferences[STARTLOCATION_KEY] = plan.startLocation
            preferences[STARTTIME_KEY] = plan.startTime
        }
    }

    fun getPlace(): Flow<Place> {
        return dataStore.data.map { preferences ->
            Place(
                preferences[PLACEID_KEY] ?: 0,
                preferences[CATEGORYID_KEY] ?: 0,
                preferences[NAME_KEY] ?: "",
                preferences[DESCRIPTION_KEY] ?: "",
                preferences[CITY_KEY] ?: "",
                preferences[PRICE_KEY] ?: 0f,
                preferences[LAT_KEY] ?: 0f,
                preferences[LNG_KEY] ?: 0f,
                preferences[RATING_KEY] ?: 0f,
                preferences[IMAGE_KEY] ?: ""
            )
        }
    }

    suspend fun savePlace(place: Place) {
        dataStore.edit { preferences ->
            preferences[PLACEID_KEY] = place.placeId
            preferences[CATEGORYID_KEY] = place.categoryId
            preferences[NAME_KEY] = place.name
            preferences[DESCRIPTION_KEY] = place.description
            preferences[CITY_KEY] = place.city
            preferences[PRICE_KEY] = place.price
            preferences[LAT_KEY] = place.lat
            preferences[LNG_KEY] = place.lng
            preferences[RATING_KEY] = place.rating
            preferences[IMAGE_KEY] = place.image
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val USERID_KEY = intPreferencesKey("user_id")
        private val USERNAME_KEY = stringPreferencesKey("username")
        private val TOKEN_KEY = stringPreferencesKey("token")

        private val PLANID_KEY = stringPreferencesKey("plan_id")
        private val TITLE_KEY = stringPreferencesKey("title")
        private val NUMOFPEOPLE_KEY = intPreferencesKey("num_of_people")
        private val CITY_KEY = stringPreferencesKey("city")
        private val STARTLOCATION_KEY = stringPreferencesKey("start_location")
        private val STARTTIME_KEY = stringPreferencesKey("start_time")

        private val PLACEID_KEY = intPreferencesKey("place_id")
        private val CATEGORYID_KEY = intPreferencesKey("category_id")
        private val NAME_KEY = stringPreferencesKey("name")
        private val DESCRIPTION_KEY = stringPreferencesKey("description")
        private val PRICE_KEY = floatPreferencesKey("price")
        private val LAT_KEY = floatPreferencesKey("lat")
        private val LNG_KEY = floatPreferencesKey("lng")
        private val RATING_KEY = floatPreferencesKey("rating")
        private val IMAGE_KEY = stringPreferencesKey("image")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}