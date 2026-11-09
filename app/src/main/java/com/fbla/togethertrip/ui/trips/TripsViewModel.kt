package com.fbla.togethertrip.ui.trips

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fbla.togethertrip.data.model.Trip
import com.fbla.togethertrip.data.repository.TripRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripsViewModel @Inject constructor(private val repo: TripRepository) : ViewModel() {
    val trips = repo.trips().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    fun addTrip(t: Trip) = viewModelScope.launch { repo.saveTrip(t) }
    fun deleteTrip(t: Trip) = viewModelScope.launch { repo.deleteTrip(t) }
}