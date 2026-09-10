# Idle-Coding data assets

This directory contains the runtime game configuration loaded by `GameDataRepository`.

## Compatibility rules

- Keep stable IDs and keys unchanged unless the matching Kotlin logic, save migration, quests, loot tables and references are migrated together.
- Keep gameplay numbers and cross-file references intact when only changing presentation.
- Visual markers stored in JSON must be Unicode text/emoji. Do not store Android drawable names or bitmap paths such as `.png`, `.jpg`, `.webp` or `assets/sprites/...`.
- `emoji`, `icon_emoji` and the legacy `banner_icon` field are display symbols, not image resource identifiers.
- `@string/...` values are resolved by `GameDataRepository` at load time and should be used for localised display text.
- Nested metadata must stay consistent with its canonical entry. For example, a boss pet entry must refer to the same pet ID used in `pets.json`.
- New JSON files should only be added when they are consumed by `GameDataRepository` or by another explicit loader.

## Image-free UI

The app no longer requires boss or character bitmap assets for normal rendering. Data files should therefore provide text/symbol fallbacks rather than filenames. Existing coordinate data in `house_tiles.json` remains gameplay/layout metadata until the house renderer is fully migrated away from its legacy atlas model.
