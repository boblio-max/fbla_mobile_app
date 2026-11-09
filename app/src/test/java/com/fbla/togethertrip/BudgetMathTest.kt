package com.fbla.togethertrip

import com.fbla.togethertrip.util.BudgetMath
import com.fbla.togethertrip.util.Validation
import org.junit.Assert.assertEquals
import org.junit.Test

class BudgetMathTest {
    @Test
    fun splitEvenly() {
        val members = listOf("a", "b")
        val expenses = listOf(BudgetMath.ExpenseInput(100.0, "a", listOf("a", "b")))
        val bal = BudgetMath.balances2(members, expenses)
        val byId = bal.associateBy { it.memberId }
        assertEquals(100.0, byId["a"]!!.paid, 0.001)
        assertEquals(50.0, byId["a"]!!.owed, 0.001)
        assertEquals(0.0, byId["b"]!!.paid, 0.001)
        assertEquals(50.0, byId["b"]!!.owed, 0.001)
        assertEquals(50.0, byId["a"]!!.net, 0.001)
        assertEquals(-50.0, byId["b"]!!.net, 0.001)
    }

    @Test
    fun validation() {
        assertEquals("Name is required", Validation.requireNonBlank("", "Name"))
        assertEquals(null, Validation.requireNonBlank("x", "Name"))
        assertEquals("Amount must be greater than 0", Validation.positiveAmount(0.0))
        assertEquals("End date cannot be before start date", Validation.validDateRange(10L, 5L))
    }
}