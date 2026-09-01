<h3 align="center">𝗛𝗘𝗬 𝗧𝗛𝗘𝗥𝗘, 𝗜'𝗠 𝗦𝗛𝗔𝗥𝗩𝗜 👋</h3>

<h3 align="center">𝗔 𝗙𝗨𝗟𝗟 𝗦𝗧𝗔𝗖𝗞 𝗗𝗘𝗩 𝗔𝗡𝗗 𝗗𝗔𝗧𝗔 𝗔𝗡𝗔𝗟𝗬𝗦𝗧</h3>

<p align="center">This is a demo of 𝗠𝗢𝗗𝗘𝗥𝗡 𝗚𝗢𝗢𝗚𝗟𝗘 𝗦𝗜𝗚𝗡-𝗜𝗡 Bottom Sheet & 𝗙𝗜𝗥𝗘𝗕𝗔𝗦𝗘 𝗔𝗨𝗧𝗛</p>

<p align="center">
<img src="https://img.shields.io/badge/Android%20SDK-37-green?style=for-the-badge&logo=android"/>
<img src="https://img.shields.io/badge/Kotlin-1.9%2B-blue?style=for-the-badge&logo=kotlin"/>
<img src="https://img.shields.io/badge/Credential%20Manager-1.5.0-purple?style=for-the-badge"/>
<img src="https://img.shields.io/badge/Firebase-Auth-orange?style=for-the-badge&logo=firebase"/>
</p>

---
## 🗂️ 𝗔𝗕𝗢𝗨𝗧

**Category:** Android Development  
**Subcategory:** Concept Integration

😤 **𝗣𝗥𝗢𝗕𝗟𝗘𝗠** — Old-style Google account popup.

☺️ **𝗦𝗢𝗟𝗨𝗧𝗜𝗢𝗡** — Credential Manager + Bottom Sheet for a modern sign-in experience.

---

## 🫴 𝗙𝗘𝗔𝗧𝗨𝗥𝗘𝗦

* 📱 Google Account Bottom Sheet
* 🔐 Credential Manager Sign-In
* 🔥 Firebase Authentication
* 📧 Email/Password Login
* ⚡ Kotlin + XML
* 📦 minSdk 30

---

## 🤟 𝗧𝗘𝗖𝗛 𝗦𝗧𝗔𝗖𝗞

Kotlin · XML · Credential Manager · Google Identity Services · Firebase Auth

---

## 🚀 𝗦𝗘𝗧𝗨𝗣

### 𝟭. Clone

```bash
git clone https://github.com/DcodeSharvi/Android-Google-BottomSheet-Auth.git
```

Open the project in Android Studio.

### 𝟮. Create Firebase Project

Create a project in Firebase Console and add your Android app.

Enter:

* Package Name
* SHA-1
* SHA-256

### 𝟯. Enable Authentication

Go to:

```text
Firebase Console
→ Authentication
→ Sign-in method
```

Enable:

* Google
* Email/Password

### 𝟰. Add `google-services.json`

Download `google-services.json` from your Firebase project and place it here:

```text
app/google-services.json
```

### 𝟱. Google Services Plugin

Add to the root `build.gradle.kts`:

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    id("com.google.gms.google-services") version "4.5.0" apply false
}
```

Apply in the app-level `build.gradle.kts`:

```kotlin
plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}
```

### 𝟲. Run

**Sync Gradle → Build → Run ▶️**

---

👉 **Download the latest APK:**  
[Releases](../../releases)

> The APK is provided for testing and demonstration purposes only.
> 
---

## ⚙️ 𝗦𝗜𝗚𝗡-𝗜𝗡 𝗙𝗟𝗢𝗪

```text
Google Sign-In
      ↓
Credential Manager
      ↓
Account Bottom Sheet
      ↓
Google ID Token
      ↓
Firebase Authentication
```

Main implementation:

```text
LoginActivity.kt
```

---

## 📦 𝗗𝗘𝗣𝗘𝗡𝗗𝗘𝗡𝗖𝗜𝗘𝗦

```kotlin
implementation("androidx.credentials:credentials:1.5.0")
implementation("androidx.credentials:credentials-play-services-auth:1.5.0")
implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
implementation("com.google.firebase:firebase-auth-ktx:23.2.1")
```

---

<h3 align="right">
❤️ 𝗖𝗢𝗗𝗘 ☕ 𝗙𝗢𝗖𝗨𝗦 🚀 𝗚𝗥𝗢𝗪 ⭐ 𝗜𝗡𝗦𝗣𝗜𝗥𝗘
</h3>
<div align="right">
<p>

<!-- GitHub Profile Views -->
<img src="https://komarev.com/ghpvc/?username=DcodeSharvi&style=for-the-badge&color=ff69b4&label=PROFILE+VIEWS"/>
