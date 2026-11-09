# FBLA 2027 Mobile Application Development — Group Trip Planner (Android) MVP Plan

## 1. Topic & Competitive Brief
- **Event**: FBLA Mobile Application Development (HS, Presentation, Team 1-3)
- **2026-2027 Topic**: "Together We Go: Group Trip Planner" — a mobile app that helps groups organize and manage a shared trip from planning through completion. Must support collaboration, communication, scheduling, budgeting, organization, and trip management for multiple users.
- **Platform**: Google's Android (chosen for open deployment; will be smartphone-deployable).
- **Rounds**: Preliminary 3-min setup / 7-min presentation / 3-min Q&A; Final same. No electricity in prelim, internet unreliable -> must run standalone with offline backup.

## 2. Rating-Sheet Driven Requirements (110 pts)
- Design & Code Quality: planning process documented (docs), classes/modules/components (MVVM), architectural patterns (repository+MVVM+Room+Compose+Hilt), innovation (polls, offline sync, smart itinerary).
- UX: user journey mapped & rationale + accessibility (Material3, dynamic color, TalkBack labels, contrast), intuitive + instructions (onboarding, empty states), consistent icons (Material Symbols vector), input validation syntactic+semantic.
- Functionality: fully addresses group trip planning; social media integration (Android share sheet + in-app share card); run standalone; app correlation explained.
- Data: comprehensive secure storage (Room, EncryptedSharedPreferences, constraints).
- Documentation & copyright compliance: README with sources/licenses.
- Presentation delivery.

## 3. Competitive Design Decisions
- Comparable: TripIt, Splitwise, Wanderlog. Differentiator: all-in-one offline-first group coordination with polls/packing, no ads.
- Architecture: Jetpack Compose + MVVM + Kotlin Coroutines + Room + Hilt.
- Offline-first: every screen works local Room DB; export/import JSON bundle for sharing.
- Social media: native share sheet to WhatsApp/Instagram/Twitter + generated share card image.

## 4. MVP Feature Scope (TogetherTrip)
1. Trips (name, destination, dates, budget, color, notes)
2. Members (roles, colors)
3. Itinerary (day events, time, location, cost, complete toggle)
4. Budget (expenses by category, split evenly/custom, balances)
5. Polls/voting (options, emoji reactions, tally, close date)
6. Packing list (assignee, packed toggle)
7. Group notes / log (timestamped)
8. Export/Share (summary text + image card; share sheet; PDF via PrintManager; JSON bundle import/export)
9. Settings (theme, text size, notifications, About/licenses)

## 5. MVP cuts
Cut real backend multi-user sync (use local-first + bundle share); keep all UI/logic demonstrable standalone.

## 6. Module Plan
- app/src/main/java/com/fbla/togethertrip/: ui/{theme, trips, itinerary, budget, polls, packing, notes, settings, onboarding}, data/{model, db, repository}, util/{ShareHelper, Validation, Format}
- tests: JUnit for budget splitting + date validation
- README.md, LICENSES.md, planning docs

## 7. Tech Stack
Kotlin 1.9+, AGP 8.x, compileSdk 34, minSdk 24; Compose, Material3, Nav Compose, Room, Hilt, Coroutines/Flow, DataStore; no network required at runtime.

## 8. Social Media Integration
Intent ACTION_SEND text/plain + image chooser; custom share card image (Compose snapshot + FileProvider); per-app deep links user-triggered inside app only.

## 9. Timeline
Scaffold gradle -> code screens -> unit tests -> docs -> verify build.

## 10. Risks
Gradle/SDK availability: check first; fallback if needed. Venue internet unreliable: offline-first.

## 11. Deliverables Checklist
- README.md stating platform (Google's Android), run instructions, architecture, licenses
- Fully commented source
- In-app read-me (About/Help + onboarding)
- Standalone, no errors
- Smartphone-deployable APK
- Social media integration
- Planning docs
Next step: scaffold Android project." 