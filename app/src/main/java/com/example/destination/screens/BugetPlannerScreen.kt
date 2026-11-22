
package com.example.destination.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.destination.data.BudgetDetails
import com.example.destination.data.BudgetStorage
import java.util.Locale

@Composable
private fun BudgetInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            val filtered = buildString {
                var hasDecimal = false
                newValue.forEach { char ->
                    if (char.isDigit()) {
                        append(char)
                    } else if (char == '.' && !hasDecimal) {
                        append(char)
                        hasDecimal = true
                    }
                }
            }
            onValueChange(filtered)
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal
        ),
        modifier = Modifier.fillMaxWidth(),
        singleLine = true
    )
}

@Composable
fun BudgetPlannerScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current

    val savedBudget = remember {
        BudgetStorage.getBudget(context)
    }

    var totalBudget by remember {
        mutableStateOf(savedBudget.totalBudget.toString())
    }

    var transportation by remember {
        mutableStateOf(savedBudget.transportation.toString())
    }

    var food by remember {
        mutableStateOf(savedBudget.food.toString())
    }

    var accommodation by remember {
        mutableStateOf(savedBudget.accommodation.toString())
    }

    var entranceFees by remember {
        mutableStateOf(savedBudget.entranceFees.toString())
    }

    var otherExpenses by remember {
        mutableStateOf(savedBudget.otherExpenses.toString())
    }

    var savedMessage by remember {
        mutableStateOf(false)
    }

    val budget = BudgetDetails(
        totalBudget = totalBudget.toDoubleOrNull() ?: 0.0,
        transportation = transportation.toDoubleOrNull() ?: 0.0,
        food = food.toDoubleOrNull() ?: 0.0,
        accommodation = accommodation.toDoubleOrNull() ?: 0.0,
        entranceFees = entranceFees.toDoubleOrNull() ?: 0.0,
        otherExpenses = otherExpenses.toDoubleOrNull() ?: 0.0
    )

    fun money(value: Double): String {
        return String.format(
            Locale.getDefault(),
            "₱%.2f",
            value
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Budget Planner",
                style = MaterialTheme.typography.headlineMedium
            )

            TextButton(onClick = onBack) {
                Text("Back")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Plan your travel expenses")

        Spacer(modifier = Modifier.height(16.dp))

        BudgetInputField(
            "Total Travel Budget (₱)",
            totalBudget
        ) {
            totalBudget = it
            savedMessage = false
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            "Expenses",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        BudgetInputField(
            "Transportation (₱)",
            transportation
        ) {
            transportation = it
            savedMessage = false
        }

        Spacer(modifier = Modifier.height(12.dp))

        BudgetInputField(
            "Food (₱)",
            food
        ) {
            food = it
            savedMessage = false
        }

        Spacer(modifier = Modifier.height(12.dp))

        BudgetInputField(
            "Accommodation (₱)",
            accommodation
        ) {
            accommodation = it
            savedMessage = false
        }

        Spacer(modifier = Modifier.height(12.dp))

        BudgetInputField(
            "Entrance Fees (₱)",
            entranceFees
        ) {
            entranceFees = it
            savedMessage = false
        }

        Spacer(modifier = Modifier.height(12.dp))

        BudgetInputField(
            "Other Expenses (₱)",
            otherExpenses
        ) {
            otherExpenses = it
            savedMessage = false
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(18.dp)
            ) {
                Text(
                    "Budget Summary",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("Total Budget: ${money(budget.totalBudget)}")
                Text("Total Expenses: ${money(budget.totalExpenses)}")

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 10.dp)
                )

                Text(
                    "Remaining: ${money(budget.remainingBudget)}",
                    style = MaterialTheme.typography.titleLarge,
                    color = if (budget.remainingBudget < 0)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.primary
                )

                if (budget.remainingBudget < 0) {
                    Text(
                        "Your expenses exceed your budget.",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                BudgetStorage.saveBudget(context, budget)
                savedMessage = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Budget")
        }

        if (savedMessage) {
            Text(
                "Budget successfully saved!",
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}
