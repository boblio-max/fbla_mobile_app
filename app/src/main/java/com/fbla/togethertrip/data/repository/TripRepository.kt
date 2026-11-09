package com.fbla.togethertrip.data.repository

import com.fbla.togethertrip.data.db.*
import com.fbla.togethertrip.data.model.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TripRepository @Inject constructor(
    private val tripDao: TripDao,
    private val memberDao: MemberDao,
    private val itineraryDao: ItineraryDao,
    private val expenseDao: ExpenseDao,
    private val pollDao: PollDao,
    private val packingDao: PackingDao,
    private val noteDao: NoteDao
) {
    fun trips(): Flow<List<Trip>> = tripDao.all()
    suspend fun getTrip(id: String) = tripDao.get(id)
    suspend fun saveTrip(t: Trip) = tripDao.upsert(t)
    suspend fun deleteTrip(t: Trip) = tripDao.delete(t)

    fun members(id: String): Flow<List<Member>> = memberDao.forTrip(id)
    suspend fun saveMember(m: Member) = memberDao.upsert(m)
    suspend fun deleteMember(m: Member) = memberDao.delete(m)

    fun itinerary(id: String): Flow<List<ItineraryItem>> = itineraryDao.forTrip(id)
    suspend fun saveItinerary(i: ItineraryItem) = itineraryDao.upsert(i)
    suspend fun deleteItinerary(i: ItineraryItem) = itineraryDao.delete(i)

    fun expenses(id: String): Flow<List<Expense>> = expenseDao.forTrip(id)
    suspend fun saveExpense(e: Expense) = expenseDao.upsert(e)
    suspend fun deleteExpense(e: Expense) = expenseDao.delete(e)

    fun polls(id: String): Flow<List<Poll>> = pollDao.forTrip(id)
    suspend fun savePoll(p: Poll) = pollDao.upsert(p)
    suspend fun deletePoll(p: Poll) = pollDao.delete(p)

    fun packing(id: String): Flow<List<PackingItem>> = packingDao.forTrip(id)
    suspend fun savePacking(p: PackingItem) = packingDao.upsert(p)
    suspend fun deletePacking(p: PackingItem) = packingDao.delete(p)

    fun notes(id: String): Flow<List<GroupNote>> = noteDao.forTrip(id)
    suspend fun saveNote(n: GroupNote) = noteDao.upsert(n)
    suspend fun deleteNote(n: GroupNote) = noteDao.delete(n)
}