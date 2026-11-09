package com.fbla.togethertrip.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fbla.togethertrip.data.model.Trip
import com.fbla.togethertrip.data.model.Member
import com.fbla.togethertrip.data.model.ItineraryItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TripDao {
    @Query("SELECT * FROM trips ORDER BY createdAt DESC")
    fun all(): Flow<List<Trip>>
    @Query("SELECT * FROM trips WHERE id = :id")
    suspend fun get(id: String): Trip?
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(t: Trip)
    @Delete
    suspend fun delete(t: Trip)
}

@Dao
interface MemberDao {
    @Query("SELECT * FROM members WHERE tripId = :tripId")
    fun forTrip(tripId: String): Flow<List<Member>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(m: Member)
    @Delete
    suspend fun delete(m: Member)
}

@Dao
interface ItineraryDao {
    @Query("SELECT * FROM itinerary WHERE tripId = :tripId ORDER BY dayIndex, timeMillis")
    fun forTrip(tripId: String): Flow<List<ItineraryItem>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(i: ItineraryItem)
    @Delete
    suspend fun delete(i: ItineraryItem)
}