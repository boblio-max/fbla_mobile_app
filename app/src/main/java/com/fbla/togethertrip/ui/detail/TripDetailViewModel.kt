package com.fbla.togethertrip.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fbla.togethertrip.data.model.*
import com.fbla.togethertrip.data.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: TripRepository
) : ViewModel() {
    val tripId: String = savedStateHandle.get<String>("tripId")!!
    val trip: StateFlow<Trip?> = repo.trips().map { it.firstOrNull { t -> t.id == tripId } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    val members = repo.members(tripId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val itinerary = repo.itinerary(tripId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val expenses = repo.expenses(tripId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val polls = repo.polls(tripId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val packing = repo.packing(tripId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    val notes = repo.notes(tripId).stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun saveMember(m: Member) = viewModelScope.launch { repo.saveMember(m) }
    fun deleteMember(m: Member) = viewModelScope.launch { repo.deleteMember(m) }
    fun saveItinerary(i: ItineraryItem) = viewModelScope.launch { repo.saveItinerary(i) }
    fun deleteItinerary(i: ItineraryItem) = viewModelScope.launch { repo.deleteItinerary(i) }
    fun saveExpense(e: Expense) = viewModelScope.launch { repo.saveExpense(e) }
    fun deleteExpense(e: Expense) = viewModelScope.launch { repo.deleteExpense(e) }
    fun savePoll(p: Poll) = viewModelScope.launch { repo.savePoll(p) }
    fun deletePoll(p: Poll) = viewModelScope.launch { repo.deletePoll(p) }
    fun savePacking(p: PackingItem) = viewModelScope.launch { repo.savePacking(p) }
    fun deletePacking(p: PackingItem) = viewModelScope.launch { repo.deletePacking(p) }
    fun saveNote(n: GroupNote) = viewModelScope.launch { repo.saveNote(n) }
    fun deleteNote(n: GroupNote) = viewModelScope.launch { repo.deleteNote(n) }
    fun saveTrip(t: Trip) = viewModelScope.launch { repo.saveTrip(t) }
}