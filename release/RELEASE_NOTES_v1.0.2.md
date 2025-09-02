# OpenSRP Client OPD 1.0.2

Release date: 2025-09-02

## Fixes
- SnakeYAML compatibility: initialize YAML loader to work with SnakeYAML 2.x and 1.x; adjust `readYaml` to load documents reliably across versions.
- French resources: refine overrides to enforce positional formatting for multi-argument strings and avoid aapt2 issues.

## Coordinates
```groovy
implementation 'io.github.bluecodesystems:opensrp-client-opd:1.0.2'
```

## Build / Publish
- Build AAR: `./gradlew :opensrp-opd:assembleRelease`
- Publish to Maven Local: `./gradlew :opensrp-opd:publishToMavenLocal`
- Zip AAR+POM+sources+javadoc: `./gradlew :opensrp-opd:packageReleaseZip`

