
# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

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
