# Template.Android

A template for creating Android projects at Q42.

## Features

Only basic features that almost all projects use, were added in this template:
- compose
- theming
- screen navigation
- dependency injection
- networking
- json parsing
- action results
- clean architecture

## Contributing

To contribute, simply create a PR and let developers from other projects approve and merge it.
A note on changes: these should be practical and pragmatic. Do not add complexity for the sake of aesthetics.

## Setup decisions

### Clean architecture

We use Clean Architecture, to a very large extend. The setup:

![Screenshot 2023-01-30 at 16 48 09](https://user-images.githubusercontent.com/2270625/215524950-d65bf0ce-1f7a-4c81-a2fa-d6dd4b757420.png)

#### Clean Architecture layers

- UI with Jetpack Compose
- Presentation with the ViewModel
- Domain for domain models, UseCases and other domain logic.
- Data for data storage and retrieval.

##### Clean Architecture Module Dependencies
- All dependency arrows point towards Domain. Domain has no dependencies on other modules. This is a Clean Architecture rule. Domain is the most stable module in this architecture. In this
  hierarchy, domain is not affected by changes in other layers that are less stable. 
- UI and Data are _outer layers_ and _unstable_. In this setup, the many changes we do here will have less effect on other,
  more stable modules.
- We chose to not have a Presentation module in this setup, to not have an overload of modules. The ViewModel lives in the feature
  module, in a _feature/presentation_ package. The view classes are in a _feature/ui_ package.
- The _app_ module is not drawn in this schema because it should only be used to start the
  app and specify the theme. Do not put code in the app module, because in the future, more app-types might be added (like tv, automotive, instant). 

#### mapping

- Mapping of models is done before handing over the model to an inner layer, as you can see at the
  right of the diagram.

#### use cases

- Use cases are single-purpose: GetUserUseCase, but also: GetUserWithArticlesUseCase.
- Use cases can call other use-cases.
- Use cases do not have state, state preferably lives in the data layer.

### Key architecture patterns and principles

- Unidirectional Data Flow: State up, events down
- Single responsibility principle
- Separation of concerns

### Groovy instead of kotlin script build files

In January 2023: We wrote gradle files in KTS. But many things are cumbersome using KTS, like
sharing gradle files between modules in a includeBuild: adds more steps to take, per shared file.
Rolled it back to Groovy, let's try again in
a year?

### No non-android modules

We do not use non-android modules because we want to use Hilt across all modules. There are
workarounds to do this in non-android modules, but then the developer would need to implement a
class differently based on the module type. We prefer to clarity over faster compile time, for
now.

### Version catalog

We use a version catalog (toml) file for versioning. This is the latest feature from Gradle
currently. It can be shared over projects.

### Dependency injection using Hilt

Because we use Hilt, and do not want the domain layer to know the data layer, app must know all
modules.

### Image loading

Use Coil or Landscapist-Coil. Currently, Glide had memory issues with compose at HEMA. TODO what
exactly?

### DTO Models

Data transfer modules are preferably generated from server source/json/schemas and do not contain
do not contain any changes compared to the contract.

### Networking

We choose Retrofit over Ktor because we already use it in all of our projects and have a positive
experience with it. Retrofit does not (and probably will not) support multiplatform, so we might
switch to Ktor in the future.

### JSON parsing

We use [Moshi](https://github.com/square/moshi) and moshi code generation for all json parsing
because it is fast, modern and has many extensions available, like support for sealed classes.
Make sure to use `@JsonClass(generateAdapter = true)` on your models, it greatly improves parsing
speed.
For date parsing you can use `com.squareup.moshi:moshi-adapters`. To keep the template small, it is
not included yet.

### BuildConfig

BuildConfig (or gralde build type configuration) is only allowed in the app module,
for [clarity and to avoid bugs]( * https://blog.dipien.com/stop-generating-the-buildconfig-on-your-android-modules-7d82dd7f20f1
). If you need the config in a different module, use dependency injection and our `ConfigModule`.

### Navigation

We use [compose destinations](https://github.com/raamcosta/compose-destinations) for networking because it's low risk, high gain:
- It reduces the amount of custom logic and effort needed to use compose-navigation. 
- It's a layer over compose-navigation, so we can later easily switch to compose-navigation if we want to.

### Theming
Tell your designers that there is a [Figma material theme builder plugin](https://www.figma.com/community/plugin/1034969338659738588)
And a [Figma design kit](https://www.figma.com/community/file/1035203688168086460)
Or you might want to generate a [Compose theme on the web](https://m3.material.io/theme-builder#/dynamic).
Nice article to read is [Introducing Material Theme Builder](https://m2.material.io/material-theme-builder/)


#### Inspiration Material

- [Clean Architecture book](https://www.amazon.nl/Clean-Architecture-Craftsmans-Software-Structure/dp/0134494164/ref=sr_1_1?__mk_nl_NL=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=3PL4CNRB0N4UI&keywords=Robert+C.+Martin+Series+-+Clean+Architecture&qid=1675245338&sprefix=robert+c.+martin+series+-+clean+architecture%2Caps%2C54&sr=8-1)
  - Quite a nice book that is easy to read. If you've read many articles on the subject, then this
  book might still teach you a few things.
- [Google Architecture Guidelines](https://developer.android.com/topic/architecture) - We largely
  follow these, with some adjustments.
