# Color Parser

This script converts color tokens from Figma to Compose format. It is designed to be used with the
figma 'Color2Code' plugin, which exports the Color Tokens.

## Requirements

- Android Studio
- 'Color2Code' plugin for Figma
- command line kotlin (`brew install kotlin`)

## Usage

1. Open
   the [Color Tokens page](https://www.figma.com/file/jTFWg6tZjCWwcxqwkKmykZ/app-foundation?type=design&node-id=1259-12&mode=design&t=BcdgPKHmtGLKMZL8-11)
   in Figma.
1. Run the [Color2Code](https://www.figma.com/community/plugin/1262830857362718014) plugin in Figma.
   Note: you need edit rights for this, perhaps you need to do
   this in collaboration with a designer.
2. Export the Color Tokens using the plugin. Disable `Remove group names`; enable
   `Camel case naming` select `Android` and `Semantics`.
   ![Color 2 Code settings](color_parser_color_2_code.png)
3. Copy the output of the plugin and paste it into the `color_parser_input.txt` file.
4. Run the `ColorParser.kts` script by clicking the play button in the file or running

   `./scripts/colorparser/ColorParser.kts`

5. The script will update the `AppColorScheme.kt` file and the corresponding files for both the
   light and dark themes.

## File Structure

- `color_parser_input.kt`: Paste the output of the 'Color2Code' plugin here.
- `ColorParser.kts`: Script to parse the color tokens and generate the Compose code.
  `
