package com.fbla.togethertrip.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "trips")
data class Trip(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String,
    val destination: String,
    val startDate: Long,
    val endDate: Long,
    val budget: Double,
    val coverColor: Int = 0,
    val notes: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "members", primaryKeys = ["tripId", "id"])
data class Member(
    val tripId: String,
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val role: String = "member", // owner | member
    val contact: String = "",
    val color: Int = 0
)

@Entity(tableName = "itinerary", primaryKeys = ["tripId", "id"])
data class ItineraryItem(
    val tripId: String,
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val dayIndex: Int = 0,
    val timeMillis: Long? = null,
    val location: String = "",
    val notes: String = "",
    val cost: Double = 0.0,
    val completed: Boolean = false
)

@Entity(tableName = "expenses", primaryKeys = ["tripId", "id"])
data class Expense(
    val tripId: String,
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val amount: Double,
    val category: String = "General",
    val paidByMemberId: String? = null,
    val splitBetween: String = "", // comma ids
    val date: Long = System.currentTimeMillis(),
    val notes: String = ""
)

@Entity(tableName = "polls", primaryKeys = ["tripId", "id"])
data class Poll(
    val tripId: String,
    val id: String = UUID.randomUUID().toString(),
    val question: String,
    val options: String, // pipe separated
    val votes: String = "", // member:option index pairs pipe
    val closesAt: Long? = null,
    val emoji: String = "📊"
)

@Entity(tableName = "packing", primaryKeys = ["tripId", "id"])
data class PackingItem(
    val tripId: String,
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val assigneeId: String? = null,
    val packed: Boolean = false
)

@Entity(tableName = "notes", primaryKeys = ["tripId", "id"])
data class GroupNote(
    val tripId: String,
    val id: String = UUID.randomUUID().toString(),
    val author: String,
    val content: String,
    val createdAt: Long = System.currentTimeMillis()
)