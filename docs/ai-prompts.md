## AI Prompts

Let's collect some useful AI prompts for code generation or other tasks.

### Creating a new module

... TODO

### Creating a new screen, adding it to the navigation graph and creating a XViewModel, XViewState, XScreen, XContent set for it.

... TODO

### Migrating a fragment from XML to compose.

```

X = ..
Y = ..

Migrate XFragment to jetpack compose. 
- Take YFragment, YScreen and subcomposables as an example, where this was already done. 
- Create many subcomposables, use existing composables when possible, like NormalButton. Create many files. 
- Send the ViewModel as parameter to the XScreen composable, not deeper. 
- The view model’s viewState / uiState should be a mutablestateflow, like in the YModel. To adjust the upstate, use the `.update {}` function, not `value =.`
- Add previews to XContent and other composable, not to XScreen.
```
