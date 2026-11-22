package com.example.destination.data

import android.content.Context

object FavoritesStorage {

    private const val PREFS = "TourEaseFavorites"
    private const val KEY = "favorite_ids"

    fun getFavoriteIds(context: Context): Set<Int> {
        val prefs = context.getSharedPreferences(
            PREFS,
            Context.MODE_PRIVATE
        )

        return prefs.getStringSet(KEY, emptySet())
            ?.mapNotNull { it.toIntOrNull() }
            ?.toSet()
            ?: emptySet()
    }

    fun toggleFavorite(context: Context, id: Int) {
        val favorites = getFavoriteIds(context).toMutableSet()

        if (id in favorites) {
            favorites.remove(id)
        } else {
            favorites.add(id)
        }

        prefs(context).edit()
            .putStringSet(
                KEY,
                favorites.map { it.toString() }.toSet()
            )
            .apply()
    }

    fun removeFavorite(context: Context, id: Int) {
        val favorites = getFavoriteIds(context).toMutableSet()
        favorites.remove(id)

        prefs(context).edit()
            .putStringSet(
                KEY,
                favorites.map { it.toString() }.toSet()
            )
            .apply()
    }

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
}