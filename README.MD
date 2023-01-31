# Template.Android

## Setup decisions

### Clean architecture

...
![Screenshot 2023-01-30 at 16 48 09](https://user-images.githubusercontent.com/2270625/215524950-d65bf0ce-1f7a-4c81-a2fa-d6dd4b757420.png)

### Key architecture patterns and principles

- Unidirectional Data Flow: State up, events down
- Single responsibility principle
- Separation of concerns principle

### Kotlin script build files

In January 2022: We wrote gradle files in KTS. But many things are cumbersome using KTS, like
sharing gradle files between modules in a includeBuild: adds more steps to take, per shared file.
Rolled it back to Groovy, let's try again in
a year?

### Non-android modules

...

### Version catalog

...

### Dependency injection using Hilt, with non-android modules

...
