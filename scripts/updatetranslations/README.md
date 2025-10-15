# Translation Updater

Fetches and updates translation files from PO Editor.

## Requirements

- command line kotlin (`brew install kotlin`)

## Usage

1. Set the `POEDITOR_API_KEY` in the scrip to your PoEditor API key.
2. Set the `POEDITOR_PROJECT_ID` in the script to your PoEditor Project Id.
2. Modify the `languages` list in the script to include the desired language codes.
3. Run the script from the root of the project:

```bash
kotlin scripts/updatetranslations/updateTranslations.main.kts
```

### Options

**Allow missing translations (default):**

```bash
kotlin scripts/updatetranslations/updateTranslations.main.kts
```

**Fail on missing translations:**

```bash
kotlin scripts/updatetranslations/updateTranslations.main.kts -noMissingTranslations
```

When the `-noMissingTranslations` flag is used, the script will exit with an error if any
translations are empty/missing.

## What it does

1. Downloads translation files from PoEditor API for Dutch (nl) and other specified languages.
2. Processes the XML content:
    - Removes empty translation strings (Android falls back to base language)
    - Replaces dots with underscores in string names (Android compatibility)
    - Converts iOS-style parameters (`%@`) to Android format (`%s`)
    - Removes server-specific and iOS-specific strings
    - Cleans up formatting and comments
3. Writes the translations to `/core/ui/src/main/res/values[-xx]/strings.xml`
