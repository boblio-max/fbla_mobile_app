package com.fbla.togethertrip.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fbla.togethertrip.data.model.Trip
import com.fbla.togethertrip.data.model.Member
import com.fbla.togethertrip.data.model.ItineraryItem
import com.fbla.togethertrip.data.model.Expense
import com.fbla.togethertrip.data.model.Poll
import com.fbla.togethertrip.data.model.PackingItem
import com.fbla.togethertrip.data.model.GroupNote

@Database(
    entities = [Trip::class, Member::class, ItineraryItem::class, Expense::class, Poll::class, PackingItem::class, GroupNote::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tripDao(): TripDao
    abstract fun memberDao(): MemberDao
    abstract fun itineraryDao(): ItineraryDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun pollDao(): PollDao
    abstract fun packingDao(): PackingDao
    abstract fun noteDao(): NoteDao
}