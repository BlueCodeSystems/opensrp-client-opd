# OpenSRP Client OPD 1.0.1

Release date: 2025-09-02

## Fixes
- French resources: add positional formatting overrides for multi-argument strings and correct quoting to satisfy aapt2.
- Keep "fr" packaged alongside "en" while ensuring the library builds cleanly.

## CI
- JitPack builds publish the library's maven publication to Maven Local with `-PskipSample=true -PskipSigning=true`, avoiding sample/secrets and signing in CI.

## Coordinates
```groovy
implementation 'io.github.bluecodesystems:opensrp-client-opd:1.0.1'
```

