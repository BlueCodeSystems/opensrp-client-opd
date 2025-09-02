
# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2025-09-02

### Breaking Changes
- Increase `minSdk` to 24 and require Java 17 (source/target). Consumers must compile with JDK 17 and support API 24+.
- Migrate dialog themes to MaterialComponents overlays; apps may need to align their app theme and AlertDialog styles.

### Features
- Add reusable publishing/signing/zip flow:
  - Shared `maven.gradle` with Sonatype (s01) publish config and GPG signing via `gpg`.
  - Android `publishing.singleVariant("release")` with sources and javadoc jars.
  - New `packageReleaseZip` task to bundle release AAR, POM, sources, and javadoc jars.

### Bug Fixes
- `VisitDao`: fix incorrect column name (`created_at`) and improve SQL string; use safe "Today" string retrieval in environments without initialized library context.
- Add null-safety and fallbacks in `OpdJsonFormUtils`, `OpdLibrary`, `OpdCloseFormProcessing`, `OpdDiagnoseAndTreatFormProcessor`, and presenters for unit test environments.
- `AppExecutors`: run tasks directly when no Android main looper (plain JVM tests).
- `OpdMiniClientProcessorForJava`: centralize event extraction, validate client existence for CHECK_IN, and improve error messages.

### Dependencies
- Upgrade `com.android.tools:desugar_jdk_libs` to 1.2.2.
- Test stack: Robolectric 4.12.2; AndroidX test core 1.6.1; Fragment 1.6.2 and `fragment-testing`.
- Replace legacy Saferoom dependency; add BlueCode CircleProgressbar artifact; add excludes to prevent duplicate core.

### Chore
- Populate missing POM settings in `gradle.properties` (name, artifactId, packaging).
- Apply shared `maven.gradle` in `opensrp-opd` module.

## [0.2.0] - 2025-09-01

### Breaking Changes
- Migrate from `org.smartregister.clientandeventmodel.Event` to `org.smartregister.domain.Event` throughout the library and sample.
- Convert events created via `JsonFormUtils.createEvent(...)` to domain `Event` using `EcSyncHelper.convert(...)` for persistence/processing.
- Remove explicit `Form` extras when launching jsonwizard forms; rely on JSON payload and jsonwizard defaults.
- Adjust `BaseOpdFormFragment` to avoid a direct dependency on `Form` type; Snackbar view IDs switched to Material component IDs.
- Use `org.smartregister.R.layout.smart_register_pagination` in `OpdRegisterProvider`.
- Build system upgrade to AGP 8.4 / Gradle 8.7 and target SDK 34 may require consumer projects to update their Android/Gradle setup.

### Features
- Add UI resources for search and due-only filter (drawables and IDs).
- Extend colors and styles, including alert dialog and full-screen dialog themes.

### Bug Fixes
- Add null-safety for missing event dates in `VisitUtils` to prevent crashes.

### Dependencies
- Android Gradle Plugin 8.4.0; Gradle wrapper 8.7.
- AndroidX Espresso 3.5.1; AndroidX JUnit Ext 1.1.5.
- Add explicit `androidx.sqlite` dependencies; Google Mobile Vision 20.1.3.
- Replace `jcenter()` with `mavenCentral()`/`gradlePluginPortal()`.

### Chore
- Set `VERSION_NAME` to `0.2.0` and update group ID to `io.github.bluecodesystems`.
