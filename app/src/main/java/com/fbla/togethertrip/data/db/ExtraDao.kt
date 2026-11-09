package com.fbla.togethertrip.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fbla.togethertrip.data.model.Expense
import com.fbla.togethertrip.data.model.Poll
import com.fbla.togethertrip.data.model.PackingItem
import com.fbla.togethertrip.data.model.GroupNote
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses WHERE tripId = :tripId ORDER BY date DESC")
    fun forTrip(tripId: String): Flow<List<Expense>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(e: Expense)
    @Delete
    suspend fun delete(e: Expense)
}

@Dao
interface PollDao {
    @Query("SELECT * FROM polls WHERE tripId = :tripId")
    fun forTrip(tripId: String): Flow<List<Poll>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(p: Poll)
    @Delete
    suspend fun delete(p: Poll)
}

@Dao
interface PackingDao {
    @Query("SELECT * FROM packing WHERE tripId = :tripId")
    fun forTrip(tripId: String): Flow<List<PackingItem>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(p: PackingItem)
    @Delete
    suspend fun delete(p: PackingItem)
}

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE tripId = :tripId ORDER BY createdAt DESC")
    fun forTrip(tripId: String): Flow<List<GroupNote>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(n: GroupNote)
    @Delete
    suspend fun delete(n: GroupNote)
}