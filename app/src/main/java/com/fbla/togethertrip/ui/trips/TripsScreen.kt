package com.fbla.togethertrip.ui.trips

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fbla.togethertrip.data.model.Trip
import com.fbla.togethertrip.ui.navigation.Routes
import com.fbla.togethertrip.util.Format
import com.fbla.togethertrip.util.Validation
import kotlinx.coroutines.launch
import java.util.UUID

@Composable
fun TripsScreen(nav: NavController, vm: TripsViewModel = hiltViewModel()) {
    val trips by vm.trips.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var err by remember { mutableStateOf<String?>(null) }
    Scaffold(topBar = { TopAppBar(title = { Text("TogetherTrip") }) },
        floatingActionButton = { FloatingActionButton(onClick = { showDialog = true }) { Icon(Icons.Filled.Add, "New trip") } }) { pad ->
        Column(Modifier.padding(pad).fillMaxSize()) {
            if (trips.isEmpty()) Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text("No trips yet. Tap + to plan one.")
            } else LazyColumn(Modifier.fillMaxSize().padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(trips) { t ->
                    ElevatedCard(Modifier.fillMaxWidth().clickable { nav.navigate(Routes.detail(t.id)) }) {
                        Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Column {
                                Text(t.name, style = MaterialTheme.typography.titleMedium)
                                Text(t.destination, style = MaterialTheme.typography.bodySmall)
                                Text("${Format.date(t.startDate)} → ${Format.date(t.endDate)}", style = MaterialTheme.typography.bodySmall)
                            }
                            IconButton(onClick = { vm.deleteTrip(t) }) { Icon(Icons.Filled.Delete, "Delete") }
                        }
                    }
                }
            }
        }
    }
    if (showDialog) {
        var name by remember { mutableStateOf("") }
        var dest by remember { mutableStateOf("") }
        var budget by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("New Trip") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(name, { name = it }, label = { Text("Trip name") })
                    OutlinedTextField(dest, { dest = it }, label = { Text("Destination") })
                    OutlinedTextField(budget, { budget = it }, label = { Text("Budget") })
                    err?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    val e = Validation.requireNonBlank(name, "Name") ?: Validation.requireNonBlank(dest, "Destination")
                    if (e != null) { err = e; return@TextButton }
                    val b = budget.toDoubleOrNull() ?: 0.0
                    val now = System.currentTimeMillis()
                    vm.addTrip(Trip(id = UUID.randomUUID().toString(), name = name, destination = dest, startDate = now, endDate = now + 7L*86400000, budget = b))
                    showDialog = false; err = null
                }) { Text("Create") }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancel") } }
        )
    }
}