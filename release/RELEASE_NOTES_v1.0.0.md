# OpenSRP Client OPD 1.0.0

Release date: 2025-09-02

## Breaking Changes
- Raise minimum SDK to 24 and require Java 17 for compilation (source/target). Consumers must compile with JDK 17 and support API 24+.
- Migrate dialog themes to MaterialComponents overlays; apps may need to align app theme/AlertDialog styles.

## Features
- Reusable publishing/signing/zip flow:
  - Shared `maven.gradle` with Sonatype (s01) publishing and GPG signing via `gpg`.
  - Android `publishing.singleVariant("release")` with sources and javadoc jars.
  - New `packageReleaseZip` task to bundle release AAR, POM, sources, and javadoc.

## Bug Fixes
- `VisitDao`: correct `created_at` column name and SQL; safe "Today" string retrieval when library context isn’t initialized.
- Add null-safety and fallbacks in `OpdJsonFormUtils`, `OpdLibrary`, `OpdCloseFormProcessing`, `OpdDiagnoseAndTreatFormProcessor`, presenters for unit test environments.
- `AppExecutors`: execute directly when no Android main looper in JVM tests.
- `OpdMiniClientProcessorForJava`: centralize event extraction, validate client existence for CHECK_IN, clearer error messages.

## Dependencies
- `com.android.tools:desugar_jdk_libs` 1.2.2.
- Test stack: Robolectric 4.12.2; AndroidX test core 1.6.1; Fragment 1.6.2 + `fragment-testing`.
- Replace legacy Saferoom; add BlueCode CircleProgressbar; excludes to prevent duplicate core deps.

## Coordinates
```groovy
implementation 'io.github.bluecodesystems:opensrp-client-opd:1.0.0'
```

## Build / Publish
- Build AAR: `./gradlew :opensrp-opd:assembleRelease`
- Publish to Maven Local: `./gradlew :opensrp-opd:publishToMavenLocal`
- Zip AAR+POM+sources+javadoc: `./gradlew :opensrp-opd:packageReleaseZip` (see `opensrp-opd/build/releasePackage`)

### Central (Sonatype)
- Local Central bundle zip (manual upload):
```
./gradlew -PcentralBundle=true -PuseGpgCmd=true :opensrp-opd:publishReleasePublicationToCentralBundleRepository :opensrp-opd:generateCentralBundleChecksums :opensrp-opd:zipCentralBundle
```
- Remote publish to Sonatype (staging):
```
export ORG_GRADLE_PROJECT_sonatypeUsername='TOKEN_USER'
export ORG_GRADLE_PROJECT_sonatypePassword='TOKEN_PASS'
./gradlew :opensrp-opd:publish -PuseGpgCmd=true -PcentralRelease=true
./gradlew closeAndReleaseRepository
```

