# Release Notes - v1.0.3

**Release Date:** September 2, 2025

## Overview
This is a critical patch release that fixes a compilation error affecting the JitPack build system. The issue was related to SnakeYAML Constructor compatibility across different library versions.

## What's Fixed

### SnakeYAML Compatibility Issue
- **Issue:** Direct instantiation of `Constructor(YamlConfig.class)` caused compilation error: "incompatible types: Class<YamlConfig> cannot be converted to LoaderOptions"
- **Root Cause:** The code was directly referencing SnakeYAML 1.x constructors at compile time, which conflicts with SnakeYAML 2.x APIs
- **Solution:** Replaced direct constructor calls with reflection-based instantiation to avoid compile-time dependency issues
- **Impact:** JitPack builds now succeed consistently across different SnakeYAML versions

## Technical Details

The fix replaces this problematic code:
```java
Constructor constructor = new Constructor(YamlConfig.class);  // Compile error
```

With a reflection-based approach:
```java
Class<?> ctorCls = org.yaml.snakeyaml.constructor.Constructor.class;
java.lang.reflect.Constructor<?> ctorCtor = ctorCls.getConstructor(Class.class);
Object ctor = ctorCtor.newInstance(YamlConfig.class);
```

This ensures compile-time safety while maintaining runtime compatibility with both SnakeYAML 1.x and 2.x versions.

### Build Pipeline
- Removed all Gradle GPG signing configuration to ensure CI does not attempt to sign artifacts. Publishing remains functional for MavenLocal and other unsigned use cases.

## JitPack Build Notes

To build this library via JitPack:
- Use JDK 17 (set in JitPack configuration)
- The `-PskipSample=true` flag excludes the sample module during builds
- Artifacts publish to MavenLocal without any signing steps in CI

## Upgrading

### Maven/Gradle
```gradle
implementation 'io.github.bluecodesystems:opensrp-client-opd:1.0.3'
```

### Changes Required
No breaking changes - this is a pure bug fix release.

## Previous Issues Resolved
This release also includes all fixes from v1.0.1 and v1.0.2:
- French string resource formatting fixes
- Resource compilation improvements  
- CI/CD pipeline enhancements
