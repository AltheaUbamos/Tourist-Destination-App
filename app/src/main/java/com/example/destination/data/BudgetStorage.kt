
package com.example.destination.data

import android.content.Context

data class BudgetDetails(
    val totalBudget: Double = 0.0,
    val transportation: Double = 0.0,
    val food: Double = 0.0,
    val accommodation: Double = 0.0,
    val entranceFees: Double = 0.0,
    val otherExpenses: Double = 0.0
) {
    val totalExpenses: Double
        get() = transportation + food +
                accommodation + entranceFees + otherExpenses

    val remainingBudget: Double
        get() = totalBudget - totalExpenses
}

object BudgetStorage {

    private const val PREFS_NAME = "TourEaseBudget"

    fun getBudget(context: Context): BudgetDetails {

        val prefs = context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        )

        return BudgetDetails(
            totalBudget = prefs.getString("totalBudget", "0")
                ?.toDoubleOrNull() ?: 0.0,

            transportation = prefs.getString("transportation", "0")
                ?.toDoubleOrNull() ?: 0.0,

            food = prefs.getString("food", "0")
                ?.toDoubleOrNull() ?: 0.0,

            accommodation = prefs.getString("accommodation", "0")
                ?.toDoubleOrNull() ?: 0.0,

            entranceFees = prefs.getString("entranceFees", "0")
                ?.toDoubleOrNull() ?: 0.0,

            otherExpenses = prefs.getString("otherExpenses", "0")
                ?.toDoubleOrNull() ?: 0.0
        )
    }

    fun saveBudget(
        context: Context,
        budget: BudgetDetails
    ) {

        context.getSharedPreferences(
            PREFS_NAME,
            Context.MODE_PRIVATE
        ).edit()
            .putString("totalBudget", budget.totalBudget.toString())
            .putString("transportation", budget.transportation.toString())
            .putString("food", budget.food.toString())
            .putString("accommodation", budget.accommodation.toString())
            .putString("entranceFees", budget.entranceFees.toString())
            .putString("otherExpenses", budget.otherExpenses.toString())
            .apply()
    }
}
