package com.fbla.togethertrip.ui.detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fbla.togethertrip.data.model.*
import com.fbla.togethertrip.util.Format
import java.util.UUID

@Composable
fun TripDetailScreen(nav: NavController, tripId: String, vm: TripDetailViewModel = hiltViewModel()) {
    val trip by vm.trip.collectAsState()
    val members by vm.members.collectAsState()
    val itinerary by vm.itinerary.collectAsState()
    val expenses by vm.expenses.collectAsState()
    val polls by vm.polls.collectAsState()
    val packing by vm.packing.collectAsState()
    val notes by vm.notes.collectAsState()

    var tab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Itinerary", "Budget", "Polls", "Packing", "Notes", "Members")

    val ctx = androidx.compose.ui.platform.LocalContext.current
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(trip?.name ?: "Trip") },
            navigationIcon = { IconButton(onClick = { nav.popBackStack() }) { Icon(Icons.Filled.ArrowBack, "Back") } },
            actions = {
                IconButton(onClick = {
                    val t = trip ?: return@IconButton
                    val summary = "${t.name} to ${t.destination}\n${Format.date(t.startDate)} - ${Format.date(t.endDate)}\nBudget: ${Format.money(t.budget)}\nPlanned with TogetherTrip"
                    val intent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(android.content.Intent.EXTRA_SUBJECT, t.name)
                        putExtra(android.content.Intent.EXTRA_TEXT, summary)
                    }
                    ctx.startActivity(android.content.Intent.createChooser(intent, "Share trip via"))
                }) { Icon(Icons.Filled.Share, "Share trip") }
            }
        )
    }) { pad ->
        Column(Modifier.padding(pad)) {
            ScrollableTabRow(selectedTabIndex = tab) { tabs.forEachIndexed { i, t -> Tab(selected = tab == i, onClick = { tab = i }, text = { Text(t) }) } }
            when (tab) {
                0 -> ItineraryTab(tripId, itinerary, vm)
                1 -> BudgetTab(tripId, members, expenses, vm)
                2 -> PollsTab(tripId, members, polls, vm)
                3 -> PackingTab(tripId, members, packing, vm)
                4 -> NotesTab(tripId, notes, vm)
                5 -> MembersTab(tripId, members, vm)
            }
        }
    }
}

@Composable
fun ItineraryTab(tripId: String, items: List<ItineraryItem>, vm: TripDetailViewModel) {
    var show by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Button(onClick = { show = true }, Modifier.fillMaxWidth()) { Text("Add itinerary item") }
        Spacer(Modifier.height(8.dp))
        items.forEach { it ->
            Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text(it.title, style = MaterialTheme.typography.titleSmall)
                        if (it.location.isNotBlank()) Text(it.location, style = MaterialTheme.typography.bodySmall)
                        Text(Format.money(it.cost), style = MaterialTheme.typography.bodySmall)
                    }
                    Checkbox(checked = it.completed, onCheckedChange = { vm.saveItinerary(it.copy(completed = it)) })
                }
            }
        }
    }
    if (show) {
        var title by remember { mutableStateOf("") }
        var loc by remember { mutableStateOf("") }
        var cost by remember { mutableStateOf("") }
        AlertDialog(onDismissRequest = { show = false }, title = { Text("New item") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(title, { title = it }, label = { Text("Title") })
                    OutlinedTextField(loc, { loc = it }, label = { Text("Location") })
                    OutlinedTextField(cost, { cost = it }, label = { Text("Cost") })
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    vm.saveItinerary(ItineraryItem(tripId = tripId, title = title.ifBlank { "Item" }, location = loc, cost = cost.toDoubleOrNull() ?: 0.0))
                    show = false
                }) { Text("Save") }
            }, dismissButton = { TextButton(onClick = { show = false }) { Text("Cancel") } })
    }
}

@Composable
fun BudgetTab(tripId: String, members: List<Member>, expenses: List<Expense>, vm: TripDetailViewModel) {
    var show by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Button(onClick = { show = true }, Modifier.fillMaxWidth()) { Text("Add expense") }
        Spacer(Modifier.height(8.dp))
        val total = expenses.sumOf { it.amount }
        Text("Total spent: ${Format.money(total)}", style = MaterialTheme.typography.titleSmall)
        expenses.forEach { e ->
            Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column { Text(e.title); Text("${e.category} • ${Format.money(e.amount)}", style = MaterialTheme.typography.bodySmall) }
                    IconButton(onClick = { vm.deleteExpense(e) }) { Icon(Icons.Filled.Delete, "Delete") }
                }
            }
        }
    }
    if (show) {
        var title by remember { mutableStateOf("") }
        var amount by remember { mutableStateOf("") }
        var category by remember { mutableStateOf("General") }
        AlertDialog(onDismissRequest = { show = false }, title = { Text("New expense") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(title, { title = it }, label = { Text("Title") })
                    OutlinedTextField(amount, { amount = it }, label = { Text("Amount") })
                    OutlinedTextField(category, { category = it }, label = { Text("Category") })
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    vm.saveExpense(Expense(tripId = tripId, title = title.ifBlank { "Expense" }, amount = amount.toDoubleOrNull() ?: 0.0, category = category))
                    show = false
                }) { Text("Save") }
            }, dismissButton = { TextButton(onClick = { show = false }) { Text("Cancel") } })
    }
}

@Composable
fun PollsTab(tripId: String, members: List<Member>, polls: List<Poll>, vm: TripDetailViewModel) {
    var show by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Button(onClick = { show = true }, Modifier.fillMaxWidth()) { Text("Create poll") }
        polls.forEach { p ->
            Card(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
                Column(Modifier.padding(12.dp)) {
                    Text("${p.emoji} ${p.question}", style = MaterialTheme.typography.titleSmall)
                    p.options.split("|").forEachIndexed { idx, opt ->
                        val count = p.votes.split("|").count { it.endsWith(":$idx") }
                        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(opt)
                            Text("$count votes")
                        }
                    }
                }
            }
        }
    }
    if (show) {
        var q by remember { mutableStateOf("") }
        var opts by remember { mutableStateOf("") }
        AlertDialog(onDismissRequest = { show = false }, title = { Text("New poll") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(q, { q = it }, label = { Text("Question") })
                    OutlinedTextField(opts, { opts = it }, label = { Text("Options (comma separated)") })
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    vm.savePoll(Poll(tripId = tripId, question = q.ifBlank { "Poll" }, options = opts.replace(",", "|")))
                    show = false
                }) { Text("Save") }
            }, dismissButton = { TextButton(onClick = { show = false }) { Text("Cancel") } })
    }
}

@Composable
fun PackingTab(tripId: String, members: List<Member>, items: List<PackingItem>, vm: TripDetailViewModel) {
    var show by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Button(onClick = { show = true }, Modifier.fillMaxWidth()) { Text("Add packing item") }
        items.forEach { it ->
            Row(Modifier.fillMaxWidth().padding(vertical = 2.dp)) {
                Checkbox(checked = it.packed, onCheckedChange = { vm.savePacking(it.copy(packed = it)) })
                Text(it.name, Modifier.weight(1f).padding(start = 8.dp))
            }
        }
    }
    if (show) {
        var name by remember { mutableStateOf("") }
        AlertDialog(onDismissRequest = { show = false }, title = { Text("New item") },
            text = { OutlinedTextField(name, { name = it }, label = { Text("Item") }) },
            confirmButton = { TextButton(onClick = { vm.savePacking(PackingItem(tripId = tripId, name = name.ifBlank { "Item" })); show = false }) { Text("Save") } },
            dismissButton = { TextButton(onClick = { show = false }) { Text("Cancel") } })
    }
}

@Composable
fun NotesTab(tripId: String, notes: List<GroupNote>, vm: TripDetailViewModel) {
    var show by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Button(onClick = { show = true }, Modifier.fillMaxWidth()) { Text("Add note") }
        notes.forEach { n ->
            Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Column(Modifier.padding(12.dp)) {
                    Text(n.author, style = MaterialTheme.typography.labelSmall)
                    Text(n.content)
                }
            }
        }
    }
    if (show) {
        var author by remember { mutableStateOf("Me") }
        var content by remember { mutableStateOf("") }
        AlertDialog(onDismissRequest = { show = false }, title = { Text("New note") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(author, { author = it }, label = { Text("Author") })
                    OutlinedTextField(content, { content = it }, label = { Text("Note") })
                }
            },
            confirmButton = {
                TextButton(onClick = { vm.saveNote(GroupNote(tripId = tripId, author = author, content = content)); show = false }) { Text("Save") }
            }, dismissButton = { TextButton(onClick = { show = false }) { Text("Cancel") } })
    }
}

@Composable
fun MembersTab(tripId: String, members: List<Member>, vm: TripDetailViewModel) {
    var show by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().padding(12.dp)) {
        Button(onClick = { show = true }, Modifier.fillMaxWidth()) { Text("Add member") }
        members.forEach { m ->
            Card(Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                Row(Modifier.padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Column { Text(m.name); Text(m.role, style = MaterialTheme.typography.bodySmall) }
                    IconButton(onClick = { vm.deleteMember(m) }) { Icon(Icons.Filled.Delete, "Delete") }
                }
            }
        }
    }
    if (show) {
        var name by remember { mutableStateOf("") }
        var contact by remember { mutableStateOf("") }
        AlertDialog(onDismissRequest = { show = false }, title = { Text("New member") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(name, { name = it }, label = { Text("Name") })
                    OutlinedTextField(contact, { contact = it }, label = { Text("Contact") })
                }
            },
            confirmButton = {
                TextButton(onClick = { vm.saveMember(Member(tripId = tripId, name = name.ifBlank { "Member" }, contact = contact)); show = false }) { Text("Save") }
            }, dismissButton = { TextButton(onClick = { show = false }) { Text("Cancel") } })
    }
}