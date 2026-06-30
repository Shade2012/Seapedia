# Seapedia

Seapedia is an Android marketplace application built with **Jetpack Compose** and powered by a **NestJS** backend.

---

# 📱 Installation (Recommended)

If you only want to try the application, you don't need to build the project.

1. Open the **Releases** page of this repository.
2. Download the latest **APK** from the newest release.
3. Install it on your Android device.
4. Make sure the backend server is running before opening the application.

> **Minimum Android Version:** Android 7.0 (API 24)

---

# 💻 Installation from Source

## Prerequisites

Before running this project, install:

- Android Studio (Narwhal or newer recommended)
- JDK 17
- Android SDK 36
- Git
- Android Emulator **or** Physical Android Device (API 24+)

---

## Clone Repository

```bash
git clone <repository-url>
cd <project-folder>
```

Open the project in **Android Studio** and allow Gradle to finish syncing.

---

# 🔗 Backend Setup

This application requires the **NestJS backend** to be running.

Start the backend:

```bash
npm run start:dev
```

The backend runs on:

```
http://localhost:3000
```

---

# 📲 Connect Android Device

## USB Debugging

1. Connect your Android device.
2. Enable **Developer Options** and **USB Debugging**.
3. Verify the device:

```bash
adb devices
```

4. Forward port 3000:

```bash
adb reverse tcp:3000 tcp:3000
```

The app can now access the backend through:

```
http://localhost:3000
```

---

## Wireless Debugging

If using Android Studio's Wireless Debugging:

```bash
adb reverse tcp:3000 tcp:3000
```

> Your computer and Android device must be connected to the same Wi-Fi network.

---

# ▶️ Run the Application

Inside Android Studio, simply press **Run ▶**.

Or build manually:

```bash
./gradlew assembleDebug
```

Install the debug APK:

```bash
./gradlew installDebug
```

---

# 🚀 Project Workflow

```text
Clone Repository
       │
       ▼
Open in Android Studio
       │
       ▼
Gradle Sync
       │
       ▼
Start NestJS Backend
       │
       ▼
Connect Android Device
(USB / Wireless Debugging)
       │
       ▼
adb reverse tcp:3000 tcp:3000
       │
       ▼
Run Application
```

---

# 🛠️ Technologies

### Android

- Kotlin
- Jetpack Compose
- Material 3
- Navigation Compose
- ViewModel
- Hilt (Dependency Injection)
- Retrofit
- OkHttp
- Kotlinx Serialization
- Kotlinx Datetime
- DataStore
- Coil
- Compose Shimmer

### Backend

- NestJS
- TypeORM
- PostgreSQL

---

# 📄 License

This project is intended for educational and portfolio purposes.