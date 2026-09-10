# Idle Coding assets

The Android UI is intentionally image-independent. Runtime assets in this directory are data and text only.

- `data/` contains game configuration in JSON.
- `changelog.txt` contains the in-app changelog.
- Visual markers stored in JSON should use Unicode symbols/emoji (for example `☀️`, `🎃`, `💻`) instead of Android drawable names or PNG paths.
- Kotlin UI code should use Material/Compose icons, text symbols, or generated visuals rather than requiring `assets/sprites/*.png` files.
- Save-data fields that historically contain names such as `banner_icon` remain compatible, but new values should contain display symbols rather than drawable resource identifiers.

This keeps the APK smaller and prevents screens from breaking when artwork is missing.
