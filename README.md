# 🔥 TaskHabit

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0+-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Compose](https://img.shields.io/badge/Jetpack%20Compose-BOM%202024-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Material3](https://img.shields.io/badge/Material%203-Design-6200EE?style=for-the-badge)

**Android habit tracker app built with Kotlin and Jetpack Compose to help users build and maintain daily habits through streaks, categories, and progress tracking.**

---

## ⬇️ Download

<p align="center">
  <a href="https://github.com/TallesGuerra/TaskHabit/releases/tag/v1.1.0">
    <img src="https://img.shields.io/badge/Download%20APK-v1.1.0-3DDC84?style=for-the-badge&logo=android&logoColor=white" alt="Download APK"/>
  </a>
</p>

> **Android 7.0+** (API 24) required. Enable *"Install from unknown sources"* when prompted.

---

## 📸 Screenshots

<p align="center">
  <img src="docs/mockup_today.svg" alt="Today Screen" width="200"/>
</p>

---

## 📱 Features

- ✅ Create and manage daily habits with custom categories
- ✅ Today view — focused overview of what's pending for the day
- ✅ Swipe to delete habits with smooth gesture interaction
- ✅ Edit habits inline without leaving context
- ✅ Filter habits by All / Pending / Completed
- ✅ Streak tracking — consecutive days of completing a habit
- ✅ Completion history persisted with Room Database
- ✅ Automatic daily habit reset at midnight via WorkManager
- ✅ Stats screen with progress indicators and completion rate
- ✅ Badges screen to showcase achievements
- ✅ Category-based color coding across all screens
- ✅ User profile — name, email and birth date persisted via DataStore
- ✅ Custom habit schedule — pick specific days of the week
- ✅ Language support — English 🇺🇸 and Portuguese 🇧🇷
- ✅ Personalized top bar — shows user name after profile is saved
- ✅ Clean dark-themed UI with Material Design 3

---

## 🏗️ Architecture

```
com.example.taskhabit/
│
├── 📱 HabitApplication.kt              # @HiltAndroidApp + WorkManager daily reset
├── 📱 MainActivity.kt                  # Navigation host + language-aware CompositionLocalProvider
│
├── 🗄️ data/
│   ├── local/
│   │   ├── dao/                        # Room DAOs
│   │   │   ├── HabitDao.kt
│   │   │   ├── CategoryDao.kt
│   │   │   ├── HabitCompletionDao.kt
│   │   │   └── StreakDao.kt
│   │   ├── database/
│   │   │   ├── HabitDatabase.kt        # Room database + migrations
│   │   │   └── converter/
│   │   │       └── DateConverter.kt    # TypeConverter for Date ↔ Long
│   │   └── entity/
│   │       ├── Habit.kt                # frequency field (DAILY / custom days)
│   │       ├── Category.kt
│   │       ├── HabitCompletion.kt
│   │       └── Streak.kt
│   ├── preferences/
│   │   └── UserPreferencesRepository.kt  # DataStore — profile + language
│   └── repository/
│       ├── HabitRepository.kt
│       ├── CategoryRepository.kt
│       ├── HabitCompletionRepository.kt
│       └── StreakRepository.kt
│
├── 🧩 domain/
│   └── model/
│       ├── HabitWithCategory.kt
│       └── HabitWithStreak.kt
│
├── 💉 di/
│   ├── AppModule.kt                    # Provides DataStore<Preferences>
│   └── DatabaseModule.kt              # Provides DAOs, repositories
│
├── 🖼️ presentation/
│   ├── ui/
│   │   ├── components/
│   │   │   ├── BottomNavBar.kt         # Localized bottom navigation
│   │   │   └── KineticTopAppBar.kt     # Shows user name + avatar/settings nav
│   │   ├── screens/
│   │   │   ├── TodayScreen.kt          # Daily focus view
│   │   │   ├── HabitsScreen.kt         # Full habit list with swipe-to-delete
│   │   │   ├── AddHabitScreen.kt       # Habit creation + custom day picker
│   │   │   ├── EditHabitScreen.kt      # Habit editing form
│   │   │   ├── ProfileScreen.kt        # User profile (name, email, birth date)
│   │   │   ├── SettingsScreen.kt       # Language selection
│   │   │   ├── StatsScreen.kt          # Progress and completion stats
│   │   │   └── BadgesScreen.kt         # Achievements display
│   │   └── util/
│   │       └── HabitIconMapper.kt      # Maps habit names to Material icons
│   ├── viewmodel/
│   │   ├── HabitViewModel.kt           # Habit CRUD + toggle + streak logic
│   │   ├── CategoryViewModel.kt        # Category list management
│   │   └── ProfileViewModel.kt         # Profile + language management
│   └── worker/
│       └── ResetHabitsWorker.kt        # WorkManager — resets habits at midnight
│
└── 🎨 ui/theme/
    ├── Color.kt
    ├── Strings.kt                      # EN and PT string sets + LocalStrings
    ├── Theme.kt
    └── Type.kt
```

### Design Principles Applied

- **MVVM Architecture** — clear separation between UI and business logic
- **Repository Pattern** — single source of truth for each data domain
- **Unidirectional Data Flow** — state flows down via StateFlow, events flow up via lambdas
- **Dependency Injection** — Hilt wires all dependencies automatically
- **Single Responsibility** — each file has one focused purpose

---

## 🔧 Technologies

| Technology              | Version       | Description                                  |
|-------------------------|---------------|----------------------------------------------|
| **Kotlin**              | 2.0.21        | Modern, concise Android language             |
| **Jetpack Compose**     | BOM 2024.09   | Declarative and reactive UI toolkit          |
| **Material 3**          | Latest        | Google's latest design system                |
| **Room**                | 2.6.1         | SQLite abstraction with compile-time safety  |
| **DataStore**           | 1.1.1         | Jetpack key-value persistence (profile/lang) |
| **Hilt**                | 2.56.2        | Compile-time dependency injection            |
| **WorkManager**         | 2.9.1         | Guaranteed background task scheduling        |
| **Navigation Compose**  | 2.8.9         | Type-safe in-app navigation                  |
| **Coroutines**          | 1.7.3         | Asynchronous and concurrent programming      |
| **StateFlow / Flow**    | —             | Reactive state and stream management         |
| **KSP**                 | 2.0.21-1.0.26 | Annotation processing for Room and Hilt      |
| **Android SDK**         | 24+           | Compatible with 95%+ of active devices       |

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog (2023.1.1) or higher
- JDK 17+
- Android SDK 36
- Physical device or emulator with API 24+

### Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/TallesGuerra/TaskHabit.git
   cd TaskHabit
   ```

2. **Open in Android Studio**
   - File → Open → Select the project folder

3. **Sync dependencies**
   - Gradle will sync automatically on first open

4. **Run the app**
   - Click **Run** ▶️ or press `Shift + F10`
   - Select a device or emulator

---

## 💡 Concepts Demonstrated

- **MVVM** with `@HiltViewModel` and `StateFlow` for reactive, lifecycle-aware state
- **Room Database** with entities, DAOs, TypeConverters, foreign keys, and schema migrations
- **DataStore Preferences** for lightweight persistence of user profile and language
- **Hilt DI** with `@Module`, `@Provides`, `@Singleton`, and `@ApplicationContext`
- **WorkManager** with `CoroutineWorker` for reliable midnight habit resets
- **Jetpack Navigation** with typed route arguments (`NavType.IntType`)
- **CompositionLocalProvider** for app-wide language injection without rebuilding the tree
- **SwipeToDismissBox** for gesture-based deletion with animated background
- **`collectAsStateWithLifecycle()`** for safe, lifecycle-scoped Flow collection in Compose
- **Coroutines** with `viewModelScope.launch` for all database and DataStore operations
- **`LazyColumn`** with `key` parameter for efficient, animated list rendering
- Modular and reusable Composable component architecture

---

## 🔄 Roadmap

- [x] Habit CRUD (create, read, update, delete)
- [x] Category system with color coding
- [x] Today screen — daily focus view
- [x] Swipe-to-delete with gesture animation
- [x] Filter bar — All / Pending / Completed
- [x] Room database with full persistence
- [x] Streak tracking with completion history
- [x] WorkManager — automatic daily reset at midnight
- [x] Stats screen with progress indicators
- [x] Badges screen
- [x] MVVM + Repository + Hilt architecture
- [x] User profile with name, email and birth date
- [x] Language support — EN / PT
- [x] Custom habit schedule (specific days of the week)
- [ ] Push notifications for daily habit reminders
- [ ] Stats with real chart data (completion over time)
- [ ] Unlockable badges based on streak milestones

---

## 👨‍💻 Author

- 📧 [talles-guerra@hotmail.com](mailto:talles-guerra@hotmail.com)
- 💼 [LinkedIn](https://www.linkedin.com/in/talles-guerra/)
- 🐙 [GitHub](https://github.com/TallesGuerra)

---

**Made with ❤️ and Jetpack Compose**
