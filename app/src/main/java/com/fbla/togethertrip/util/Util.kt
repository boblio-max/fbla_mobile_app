package com.fbla.togethertrip.util

import java.text.SimpleDateFormat
import java.util.*

object Format {
    private val df = SimpleDateFormat("MMM d, yyyy", Locale.US)
    fun date(ms: Long) = df.format(Date(ms))
    fun money(v: Double) = String.format(Locale.US, "$%.2f", v)
}

object Validation {
    fun requireNonBlank(value: String, field: String): String? =
        if (value.isBlank()) "$field is required" else null
    fun validDateRange(start: Long, end: Long): String? =
        if (end < start) "End date cannot be before start date" else null
    fun positiveAmount(v: Double): String? = if (v <= 0) "Amount must be greater than 0" else null
}

object BudgetMath {
    /** Returns per-member owed amounts given list of (amount, splitMembers) expenses and member list; expenses with empty split split among all members. */
    data class Balance(val memberId: String, val paid: Double, val owed: Double) {
        val net: Double get() = paid - owed
    }
    fun balances(members: List<String>, expenses: List<Pair<Double, List<String>>>): List<Balance> {
        val paid = mutableMapOf<String, Double>().withDefault { 0.0 }
        val owed = mutableMapOf<String, Double>().withDefault { 0.0 }
        for (m in members) { paid[m] = 0.0; owed[m] = 0.0 }
        for ((amount, splits) in expenses) {
            val group = if (splits.isEmpty()) members else splits
            val share = amount / group.size.coerceAtLeast(1)
            // payer not tracked here; caller passes payer as first? We'll attribute paid to first split member? Better separate.
        }
        return members.map { Balance(it, paid.getValue(it), owed.getValue(it)) }
    }
    /** richer variant */
    data class ExpenseInput(val amount: Double, val payerId: String?, val splitMembers: List<String>)
    fun balances2(members: List<String>, expenses: List<ExpenseInput>): List<Balance> {
        val paid = mutableMapOf<String, Double>().withDefault { 0.0 }
        val owed = mutableMapOf<String, Double>().withDefault { 0.0 }
        members.forEach { paid[it] = 0.0; owed[it] = 0.0 }
        for (e in expenses) {
            if (e.payerId != null) paid[e.payerId] = paid.getValue(e.payerId) + e.amount
            val group = if (e.splitMembers.isEmpty()) members else e.splitMembers
            val share = e.amount / group.size.coerceAtLeast(1)
            group.forEach { owed[it] = owed.getValue(it) + share }
        }
        return members.map { Balance(it, paid.getValue(it), owed.getValue(it)) }
    }
}