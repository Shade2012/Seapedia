# Seapedia

Seapedia is an Android application built with **Jetpack Compose** that communicates with a NestJS backend.

## Prerequisites

Before running this project, make sure you have installed:

* Android Studio (Narwhal or newer recommended)
* JDK 17
* Android SDK 36
* Android Emulator or a physical Android device (Android 7.0 / API 24 or above)
* Git

---

## Installation

Clone the repository:

```bash
git clone <repository-url>
cd <project-folder>
```

Open the project using **Android Studio**.

Allow Gradle to sync and download all required dependencies.

---

## Backend Connection

This application requires the NestJS backend to be running locally.

Start the backend first:

```bash
npm run start:dev
```

### Using a Physical Device (USB)

1. Connect your Android device using a USB cable.
2. Enable **Developer Options** and **USB Debugging**.
3. Verify the device is detected:

```bash
adb devices
```

4. Forward port **3000** from your computer to the Android device:

```bash
adb reverse tcp:3000 tcp:3000
```

The application can now access the backend using:

```text
http://localhost:3000
```

### Using a Physical Device (Wi-Fi Debugging)

If your device supports **Wireless Debugging**, connect it through Android Studio, then run:

```bash
adb reverse tcp:3000 tcp:3000
```

> **Note:** Your computer and Android device must be connected to the same Wi-Fi network.

---

## Build and Run

After Gradle has finished syncing, click **Run ▶** in Android Studio.

Or build the project from the terminal:

```bash
./gradlew assembleDebug
```

Install the debug APK:

```bash
./gradlew installDebug
```

---

## Project Workflow

```text
Clone Repository
        │
        ▼
Open in Android Studio
        │
        ▼
Sync Gradle
        │
        ▼
Start NestJS Backend
        │
        ▼
Connect Android Device (USB or Wireless Debugging)
        │
        ▼
adb reverse tcp:3000 tcp:3000
        │
        ▼
Run the Application
```

---

## Technologies Used

* Kotlin
* Jetpack Compose
* Material 3
* Navigation Compose
* ViewModel
* Kotlinx Serialization
* Retrofit
* OkHttp
* DataStore
* Hilt (Dependency Injection)
* Coil
* Kotlinx Datetime
* Compose Shimmer
