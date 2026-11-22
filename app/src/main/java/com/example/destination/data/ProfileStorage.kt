
package com.example.destination.data

import android.content.Context

data class UserProfile(
    val name: String = "",
    val email: String = "",
    val preference: String = "Beach"
)

object ProfileStorage {

    private const val PREFS_NAME = "TourEaseProfile"

    fun getProfile(context: Context): UserProfile {

        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        return UserProfile(
            name = prefs.getString("name", "") ?: "",
            email = prefs.getString("email", "") ?: "",
            preference = prefs.getString("preference", "Beach")
                ?: "Beach"
        )
    }

    fun saveProfile(
        context: Context,
        profile: UserProfile
    ) {

        context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        ).edit()
            .putString("name", profile.name)
            .putString("email", profile.email)
            .putString("preference", profile.preference)
            .apply()
    }
}