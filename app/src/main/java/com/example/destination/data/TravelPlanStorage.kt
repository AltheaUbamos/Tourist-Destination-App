
package com.example.destination.data

import android.content.Context
import com.example.destination.models.Itinerary
import org.json.JSONArray
import org.json.JSONObject

object TravelPlanStorage {

    private const val PREFS_NAME = "TourEasePlans"
    private const val KEY_PLANS = "saved_plans"

    fun getPlans(context: Context): List<Itinerary> {
        val prefs = context.getSharedPreferences(
            PREFS_NAME, Context.MODE_PRIVATE
        )

        val json = prefs.getString(KEY_PLANS, "[]")
        val array = JSONArray(json)

        return List(array.length()) { index ->
            val item = array.getJSONObject(index)

            Itinerary(
                id = item.getInt("id"),
                touristName = item.getString("touristName"),
                title = item.getString("title"),
                destination = item.getString("destination"),
                date = item.getString("date"),
                schedule = item.getString("schedule"),
                activity = item.getString("activity"),
                status = item.getString("status")
            )
        }
    }

    fun savePlan(context: Context, plan: Itinerary) {
        val plans = getPlans(context).toMutableList()
        plans.removeAll { it.id == plan.id }
        plans.add(plan)
        saveAll(context, plans)
    }

    fun cancelPlan(context: Context, id: Int) {
        val plans = getPlans(context).map {
            if (it.id == id) it.copy(status = "Cancelled")
            else it
        }

        saveAll(context, plans)
    }

    private fun saveAll(
        context: Context,
        plans: List<Itinerary>
    ) {
        val array = JSONArray()

        plans.forEach { plan ->
            val item = JSONObject().apply {
                put("id", plan.id)
                put("touristName", plan.touristName)
                put("title", plan.title)
                put("destination", plan.destination)
                put("date", plan.date)
                put("schedule", plan.schedule)
                put("activity", plan.activity)
                put("status", plan.status)
            }

            array.put(item)
        }

        context.getSharedPreferences(
            PREFS_NAME, Context.MODE_PRIVATE
        ).edit()
            .putString(KEY_PLANS, array.toString())
            .apply()
    }
}
