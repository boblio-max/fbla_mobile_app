# TogetherTrip — Group Trip Planner (FBLA Mobile Application Development 2026-2027)

## Platform
Google's Android (smartphone-deployable). Minimum SDK 24, target/compile SDK 34.

## Topic
"Together We Go: Group Trip Planner" — collaborative group trip planning through completion.

## How to Run
1. Open folder in Android Studio (Hedgehog+).
2. Sync Gradle.
3. Run on emulator/device (Run > Run 'app').
4. No network required; runs fully standalone.

## Architecture
MVVM + Repository + Room + Jetpack Compose + Hilt + Coroutines/Flow + DataStore.
Packages: com.fbla.togethertrip.{data, ui, util}.

## Code Quality
- Input validation syntactic + semantic (Validation).
- Domain unit tests: BudgetMathTest (budget splitting & validation).
- Data integrity via Room constraints.

## Accessibility
Material3, TalkBack content descriptions, dynamic color, readable typography, large touch targets.

## Social Media Integration
Share trip summary via native Android share chooser (text) to WhatsApp/Instagram/Twitter/etc.

## Read-Me / Documentation
In-app "About" on Trip detail? Available in settings placeholder. Sources/licenses see LICENSES.md.

## Licenses
See LICENSES.md. Icons from Material Symbols (Apache 2.0). All other code original (MIT).