# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Native Android app (Java, Gradle) that visualizes sensor data for a "smart street light" IoT project. The hardware side pushes sensor readings and a camera snapshot to Firebase Realtime Database; this app subscribes to that data and renders alerts/animations per sensor.

- Gradle root project name: `SmartInteligentProject` (note typo — kept consistent across `applicationId`, namespace, package).
- `applicationId` / `namespace` / Java package: `com.example.smartinteligentproject`.
- `compileSdk` / `targetSdk` 36, `minSdk` 23, Java 1.8 source & target, `multiDexEnabled true`.
- Firebase config is committed at `app/google-services.json` (Firebase project `smart-street-light-f3afa`). The Realtime Database URL is derived from it — there is no separate env file.

## Build / run commands

Use the Gradle wrapper from the repo root:

```
./gradlew assembleDebug              # build debug APK -> app/build/outputs/apk/debug/
./gradlew installDebug               # install on connected device/emulator
./gradlew test                       # JVM unit tests (app/src/test)
./gradlew connectedAndroidTest       # instrumented tests on a device (app/src/androidTest)
./gradlew lint                       # Android lint
./gradlew clean
```

Run a single unit test class:
```
./gradlew :app:testDebugUnitTest --tests com.example.smartinteligentproject.ExampleUnitTest
```

`local.properties` (containing `sdk.dir`) is gitignored and must exist locally — Android Studio generates it on first open.

## High-level architecture

### Activity flow
`MainActivity` (splash, 5s `Thread.sleep`) → `Model.LoginScreen` (hardcoded creds `Admin` / `asdf@1234`) → `DashboardScreen` (Material `DrawerLayout` + `NavigationView`) → one of:

- `FireSensorScreen` — fire alerts for tower 1/2, plays `R.raw.firealaram`.
- `RainSensorScreen` — rain alert, plays `R.raw.rain`.
- `UltraSensorScreen` — ultrasonic (proximity-triggered light) for tower 1/2.
- `LDRScreen` — day/night state from light sensor.
- `SignBoardActivity` — read/write two city-info strings to Firebase.
- `FireShowData` — RecyclerView history list backed by `Model.UserAdapter` + `Model.User`.

Only `MainActivity` is exported (LAUNCHER). All navigation is via explicit `Intent`s; there is no `Fragment`/Navigation Component usage despite the import in `DashboardScreen`.

### Firebase Realtime Database layout
Two distinct root references are used — keep them straight when editing:

- `SmartData` — **current/live** sensor snapshot. Sensor screens attach a `ChildEventListener` to the **root** (`SmartData`), not to a specific child, then read fields from `snapshot.child("<field>")`. Fields read across screens: `T1FireSensor`, `T2FireSensor`, `T1UltrasonicSensor`, `T2UltrasonicSensor`, `Rain`, `LDRSensor`, `img`. All values are **`String`** (`"0"`/`"1"`), not booleans/numbers — `getValue(String.class)` is used throughout.
- `Smartlight` — historical log, consumed only by `FireShowData` (via `UserAdapter` over a `List<User>`). `btnDeleteAll` calls `removeValue()` on the entire `Smartlight` node.
- `cityInformation` / `cityInformation2` — top-level string nodes read/written by `SignBoardActivity`.

There's also a dead `myRef.child("200")` reference created in several sensor screens — present but never used; do not rely on it.

### Image rendering pattern
The hardware uploads a JPEG as a Base64 string in the `img` field. Every sensor screen and `UserAdapter.onBindViewHolder` repeats the same decode:
```java
byte[] imageBytes = Base64.decode(img, Base64.DEFAULT);
Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
imageView.setImageBitmap(decodedImage);
```
The stray `ByteArrayOutputStream baos = new ByteArrayOutputStream(); byte[] imageBytes = baos.toByteArray();` lines above each decode are no-ops — preserved for now, but safe to remove if cleaning up.

### Animations & media
- GIF animations use **two** libraries inconsistently: `pl.droidsonroids.gif.GifImageView` (declared in layouts as `<pl.droidsonroids.gif.GifImageView>`, set via `setImageResource`) **and** Glide (`Glide.with(...).load(R.drawable.flame).into(...)`). When adding a new animated drawable, match the surrounding screen's choice.
- Alarms use `MediaPlayer.create(this, R.raw.<sound>)` with a one-shot `OnCompletionListener` that releases the player. `stopPlayer()` is called in `onStop()`.

### Package layout quirk
`Model/` contains both data classes (`User`, `UserAdapter`) **and** `LoginScreen` (an Activity). This is reflected in `AndroidManifest.xml` as `.Model.LoginScreen`. Don't "fix" the package without also updating the manifest entry and every `new Intent(..., LoginScreen.class)` call site.

## Conventions worth preserving

- Sensor flag comparisons are always `value.equals("1")` against `String` (not boolean/int). Match this when adding new sensors.
- New sensor screens follow the same shape: find views → get `FirebaseDatabase.getInstance().getReference("SmartData")` → attach `ChildEventListener` that handles both `onChildAdded` and `onChildChanged` with effectively duplicated bodies. Both handlers must be implemented; relying on one breaks either first-load or live-update.
- Back navigation everywhere is `Intent` → `DashboardScreen` + `finish()` (no `onBackPressed` overrides, no up-affordance via toolbar except on `DashboardScreen` itself).

## Known sharp edges (don't be surprised)

- Login is fully client-side hardcoded; there is no Firebase Auth despite `firebase-ui-auth` being on the classpath.
- `LoginScreen.btnExit` calls `Process.killProcess` + `System.exit(1)` — intentional, do not "soften" without asking.
- `app/google-services.json` and the API key inside it are committed to the repo. The project leans on Firebase database security rules rather than secret management.
- `jcenter()` is still listed in `build.gradle` repositories (read-only since 2022) alongside `mavenCentral()` and `jitpack.io` — leave it unless explicitly cleaning up; some transitive artifacts may still resolve through it.
