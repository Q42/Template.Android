# Template.Android

A template for creating Android projects at Q42.

## Features

Only basic features that almost all projects use, were added in this template:

- compose
- theming
- screen navigation (inc. multi-module navigation and navigation initiated from ViewModel)
- dependency injection
- networking
- json parsing
- action results
- clean architecture
- automated dependency updates

## Project Setup

### CI

This project uses Github Actions + fastlane for CI. There is a nice upgrade path to automatically 
deploy builds to Firebase App Distribution and Play Store.

#### Installing fastlane
A big advantage of fastlane is that the ci and publishing steps can run locally. 
Use `brew install fastlane` and then `fastlane test` to verify this installation.

#### Removing Github Actions + fastlane
Using Bitrise instead? Then you can delete:
- `./fastlane`
- `./GemFile`
- `./Gemfile.lock`
- `./.github/workflows`

## Using this template

1. Click the green "Use this template" button in the Github repo to create your own repository with
   this code template.
1. Clone the new repo locally on your machine.
1. Run `python ./scripts/rename-project.py` or `python3 ./scripts/rename-project.py` from the
   project root, to change the project name and the package names. The script will ask for your new
   project name and update all references.

## Contributing

To contribute, simply create a PR and let developers from other projects approve and merge it. A
note on changes:

- Changes should be practical and pragmatic. Do not add complexity for the sake of aesthetics.
- Additions should only be added if 90%+ of our projects use it.
- In your PR description, please include a section: "Why is this important".

## Features that still need to be added

- Research android screen transition standards + update our animations
- DTO model generation from server schema.

## Setup decisions

### Clean architecture

We use [Clean Architecture](#inspiration-material), to a very large extend. Our setup:

![Q42-CA](https://user-images.githubusercontent.com/2270625/216325003-8629f40a-c46c-43e6-ba1e-342dfb524ff9.png)

#### Clean Architecture layers

- _UI_ with Jetpack Compose
- _Presentation_ with the ViewModel
- _Domain_ for domain models, UseCases and other domain logic.
- _Data_ for data storage and retrieval.

##### Clean Architecture Module Dependencies

- All dependency arrows point towards Domain. Domain has no dependencies on other modules. This is a
  Clean Architecture rule. Domain is the most stable module in this architecture. In this
  hierarchy, domain is not affected by changes in other layers that are less stable.
- UI and Data are _outer layers_ and _unstable_. In this setup, the many changes we do here will
  have less effect on other,
  more stable modules.
- We chose to not have a Presentation module in this setup, to not have an overload of modules. The
  ViewModel lives in the feature
  module, in a _feature/presentation_ package. The view classes are in a _feature/ui_ package.
- The _app_ module is not drawn in this schema because it should only be used to start the
  app and specify the theme. Do not put code in the app module, because in the future, more
  app-types might be added (like tv, automotive, instant).

#### Use cases

- Use cases are single-purpose: GetUserUseCase, but also: GetUserWithArticlesUseCase.
- Use cases can call other use-cases.
- Use cases do not have state, state preferably lives in the data layer.

#### DTO Models

Data Transfer Models (DTO) are preferably generated from server source code/json/schemas and do not
contain
any changes compared to the server contract.

#### Model Entities

Model Entities live in the data layer and are the local storage objects.

#### Model mapping

Models map from XDTO to XEntity to X and then probably into a viewstate. Mapping is always done in
the _outside layer_, before being transported to the inner layer.
See [the diagram](#clean-architecture) for more info.

- Writing mapping functions between models, entities and DTO ojects is boring and cumbersome: let
  GitHub Copilot or some other AI tool in Android Studio generate your mapping functions.

#### Core modules

Core modules are used for _common_ logic that is shared between features and is not related to any
feature or model. If your core-feature is 1-2 classes only and most modules will use it, putting it
in Core:utils is simpler than creating a separate module. It is the most general module in the
diagram, please keep it small.

Examples of logic that can live in _core:utils_:

- extension methods on general kotlin classes
- classes related to logging

### Key architecture patterns and principles

- [Unidirectional Data Flow](https://developer.android.com/jetpack/compose/architecture#udf): State
  up, events down
- Single responsibility principle
- Separation of concerns

### Groovy instead of kotlin script build files

In January 2023: We wrote gradle files in KTS. But many things are cumbersome using KTS, like
sharing gradle files between modules in a includeBuild: adds more steps to take per shared file.
We rolled it back to Groovy, let's try again in a year?

### Feature gradle files

To share gradle config over modules and make adding more modules as simple as possible, we have 2
base files:

- `build.module.feature-and-app.gradle` for all feature modules and the app module
- `build.module.library.gradle` for all other modules (library modules)

Also, feature-specific gradle files are set up, like `build.dep.compose.gradle`.
When a feature encompasses adding multiple dependencies and/or config and you might re-use it,
please add a separate gradle file for it.

### No non-android modules

We do not use non-android modules because we want to use Hilt across all modules. We prefer to
clarity over faster compile time, for now.

Other options we considered and denied:

1. Domain and data could have been kotlin modules if we used
   a [Java inject to annotate injected constructors](https://stackoverflow.com/a/67869843).
    - Pro: cleanliness, faster compile time.
    - Con: no HiltModules possible in domain and data; The app module will have to contain
      DataModule and DomainModule to wire interfaces to their implementations.
1. We could have mixed dagger (non-android modules) with hilt (android modules). Con: Having two
   types of injection, depending on where you are, is confusing.
1. We could have used dagger only. Con: Dagger is more complex and harder to grasp for new
   developers.

If you use this template to create a _white label_ app, then you might be better off with option 1.
Then you actually might want the wiring to be outside the domain and data modules.

### Dependency injection

We use Hilt, because compared to Dagger, it's simpler to get into for new developers. It also
reduces the amount of code you need to write for DI. Because we use Hilt, and do not want the domain
layer to know the data layer, app must know all
modules.

### Version catalog

We use a version catalog (toml) file for versioning. This is the latest feature from Gradle
currently. It can be shared over (included) projects.

#### ViewStateString

ViewStateStrings enables you to move String logic from the View to the ViewModel, especially plurals
or replacement parameter logic.

### Compose Previews

Use `@PreviewLightDark` to generate two previews from one function, one dark and one light. You
probably also want to
wrap your preview content with a `PreviewAppTheme{..}` so that your previews have the correct theme
background.

### BuildConfig

BuildConfig (or gradle build type configuration) is only allowed in the app module,
for [clarity and to avoid bugs](https://blog.dipien.com/stop-generating-the-buildconfig-on-your-android-modules-7d82dd7f20f1)
. If you need the config in a different module, use dependency injection and our `app/ConfigModule`.

## Libraries

### Navigation

We use [compose destinations](https://github.com/raamcosta/compose-destinations) for navigation
because it's low risk, high gain:

- It reduces the amount of custom logic and effort needed to use compose-navigation.
- It's a layer over compose-navigation, so we can later easily switch to compose-navigation if we
  want to.

We
added [extra logic to navigate from ViewModel](https://medium.com/@ffvanderlaan/navigation-in-jetpack-compose-using-viewmodel-state-3b2517c24dde)
. To enable this for your ViewModel:

- Add a navigator `private val navigator: RouteNavigator` to the constructor of your ViewModel.
- Let your ViewModel delegate RouteNavigator: `RouteNavigator by navigator`
- Call `InitNavigator(navigator, viewModel)` from your screen.
- Call `navigateTo(destination)` from your ViewModel to navigate somewhere. There are also popUpTo
  methods, etc.

  You can call navigateTo with a `AppGraphRoutes` (in core.navigation) to navigate to the root of a
  different
  graph route.

### Networking

We choose Retrofit over Ktor because we already use it in all of our projects and have a positive
experience with it. Retrofit has many more features, but does not support Kotlin Multi Platform (
yet).

### JSON parsing

We use [Kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) for all json parsing
because it is fast, modern and has IDE integration (which warns you when you forget to add @Serializable annotations).
It also has multiplatform support, so we can use it in our KMP projects as well.

We use Napier because it's usage is close to Timber/Tolbaaken, but Napier supports KMM.

### Image loading

We did not include an image loading library is this template, because not every app might need it,
but we do have a suggestion. Use Coil or Landscapist-Coil. Currently, Glide had memory issues with
compose at HEMA.

## Theming

- You can tell designers that there is
  a [Figma material3 theme builder plugin](https://www.figma.com/community/plugin/1034969338659738588)
  .
- There also is
  a [Figma design kit for material3](https://www.figma.com/community/file/1035203688168086460).
- You could also generate
  a [material3 compose theme on the web](https://m3.material.io/theme-builder#/dynamic).
- A starting point to get reading
  is: [Introducing Material Theme Builder](https://m2.material.io/material-theme-builder/)

## Inspiration Material

- [Clean Architecture book](https://www.amazon.nl/Clean-Architecture-Craftsmans-Software-Structure/dp/0134494164/ref=sr_1_1?__mk_nl_NL=%C3%85M%C3%85%C5%BD%C3%95%C3%91&crid=3PL4CNRB0N4UI&keywords=Robert+C.+Martin+Series+-+Clean+Architecture&qid=1675245338&sprefix=robert+c.+martin+series+-+clean+architecture%2Caps%2C54&sr=8-1)
    - A clear book that is easy to read. If you have read many articles on the subject, then still
      this
      book might teach you a few things.
- [Google Architecture Guidelines](https://developer.android.com/topic/architecture) - We largely
  follow these, with some adjustments. Also lists
  valuable [do's and don't's](https://developer.android.com/topic/architecture/recommendations).
