package com.fbla.togethertrip.ui.about

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AboutScreen(nav: NavController) {
    Scaffold(topBar = { TopAppBar(title = { Text("About & User Journey") }, navigationIcon = { IconButton(onClick = { nav.popBackStack() }) { Icon(Icons.Filled.ArrowBack, "Back") } }) }) { pad ->
        Column(Modifier.padding(pad).padding(16.dp).verticalScroll(rememberScrollState())) {
            Text("TogetherTrip", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
            Text("Platform: Google's Android. Standalone, offline-first. Supports collaboration, communication, scheduling, budgeting, organization, trip management.", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(12.dp))
            Text("User Journey", style = MaterialTheme.typography.titleMedium)
            Text("1. Create a trip (name, destination, dates, budget).\n2. Invite/add members.\n3. Build day-by-day itinerary.\n4. Track expenses & splits.\n5. Vote on activities via polls.\n6. Manage shared packing list.\n7. Keep group notes.\n8. Share trip summary via social apps.\n9. Adjust settings (theme, text size).", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(12.dp))
            Text("Accessibility", style = MaterialTheme.typography.titleMedium)
            Text("Material3 dynamic color, TalkBack labels, large touch targets, validation feedback.", style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(12.dp))
            Text("Libraries", style = MaterialTheme.typography.titleMedium)
            Text("AndroidX, Compose, Room, Hilt, DataStore — Apache 2.0.", style = MaterialTheme.typography.bodyMedium)
        }
    }
}