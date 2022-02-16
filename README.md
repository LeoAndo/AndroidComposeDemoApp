# Overview
templates for jetpack compose.

# MAD score

![summary](https://user-images.githubusercontent.com/16476224/150646890-a853e0b6-5123-47c9-a5ea-fe23998f61e4.png)
![jetpack](https://user-images.githubusercontent.com/16476224/150646886-f1a7a8ff-fe19-4958-9c3d-c17d370d390c.png)
![kotlin](https://user-images.githubusercontent.com/16476224/150646888-b612cad9-0128-47a4-b1f0-cbb9edc74b0c.png)
![studio](https://user-images.githubusercontent.com/16476224/150646889-1cfb8ab5-c933-4b1e-9cd3-c0e588df0f0f.png)


# Developement Environment
<img width="515" alt="スクリーンショット 2021-12-18 13 53 22" src="https://user-images.githubusercontent.com/16476224/146629502-23441780-4265-4692-ade5-2922ba22168f.png">

# App architecture

<img width="582" alt="スクリーンショット 2021-12-20 15 58 59" src="https://user-images.githubusercontent.com/16476224/146725205-ac598f5f-c5a6-4cc4-8d38-559ca50bdf77.png">

# implementation Rules

- API KEY is managed as confidential information (`local.properties`)
- ui layer
  - There is only one `Statefull @Composable function` (because check the layout in Preview depending on the state).
  - [Create a `stateless Composable` and create a `Preview Composable` for each UiState](https://github.com/LeoAndo/android-compose-templates/pull/18#discussion_r786196548)
  - [use Scaffold content paddingValues](https://github.com/LeoAndo/android-compose-templates/pull/34)
  - If simple logic, Call Repository (Interface) directly from ViewModel.
  - Error handling(try catch) is done by ViewModel.
  - [Threading](https://developer.android.com/jetpack/guide/ui-layer#threading-concurrency)
- domain layer
  - Pure Kotlin.
  - [Threading](https://developer.android.com/jetpack/guide/domain-layer#threading)
  - DO inject Dispatchers  (Default Thread)
- data layer
  - Model(Serialize).
  - Error handling and returning a custom Exception instance ([Does not Use kotlin.Result](https://github.com/LeoAndo/android-compose-templates/pull/45))
  - [Threading](https://developer.android.com/jetpack/guide/data-layer#threading)
  - DO inject Dispatchers (IO Thread)
- Proguard / R8
  - [Use only stable libraries](https://github.com/LeoAndo/AndroidAppTemplate/issues/40#issue-925121453)

# Supported OS Version

OS: 8.0 (API Level 26) or later.

# Template01

- [coding flow](https://github.com/LeoAndo/android-compose-templates/pulls?q=is%3Apr+is%3Aclosed)


- Features
  - UI
    - [Material3](https://m3.material.io/)
  - DI
    - Dagger Hilt 
  - Unit Test
    - [test](https://github.com/LeoAndo/android-compose-templates/tree/main/TemplateApp01/app/src/test/java)
    - [sharedTest](https://github.com/LeoAndo/android-compose-templates/tree/main/TemplateApp01/app/src/sharedTest/java/com/example/templateapp01) 
    - [androidTest](https://github.com/LeoAndo/android-compose-templates/tree/main/TemplateApp01/app/src/androidTest/java/com/example/templateapp01) 
  - Network
    -  (retrofit, okhttp, moshi)
  - local
    - Room
    - datastore (Under implementation)
  - Web API 
    - ([Unsplash](https://unsplash.com/documentation)) 
  - app architecture
    - single module
    - Single Activity, No Fragments
    - Navigation
      - Navigation Graph & Nest Navigation
  - other
    - [buildSrc](https://github.com/LeoAndo/android-compose-templates/pull/4)
    - [Manage secret key](https://github.com/LeoAndo/android-compose-templates/pull/9)
    - [SplashScreen](https://github.com/LeoAndo/android-compose-templates/pull/2)

- capture

| Pixel 4 OS12 |
|:---|
|<img src="https://github.com/LeoAndo/android-compose-templates/blob/main/TemplateApp01/capture/untitled.gif" width=320 /> |

# Other Android Templates
https://github.com/LeoAndo/AndroidAppTemplate<br>
https://github.com/LeoAndo/android-app-teaching-material-templates<br>

# refs

## about app architecture 
https://android-developers.googleblog.com/2021/12/rebuilding-our-guide-to-app-architecture.html?m=1<br>
https://developer.android.com/jetpack/guide/ui-layer<br>
https://developer.android.com/jetpack/guide/domain-layer<br>
https://developer.android.com/jetpack/guide/data-layer<br>

## about compose
https://developer.android.com/jetpack/compose/mental-model<br>
https://github.com/android/compose-samples<br>
https://github.com/LeoAndo/droidkaigi2021-memo/labels/Jetpack%20Compose<br>
https://github.com/LeoAndo/droidkaigi2021-memo/issues?q=is%3Aopen+is%3Aissue+label%3A%22Jetpack+Compose+1.1%22<br>
https://developer.android.com/jetpack/compose/text?hl=ja<br>

## about material3
https://m3.material.io/<br>
https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary<br>
https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/material3/material3/samples/src/main/java/androidx/compose/material3/samples/NavigationBarSamples.kt?hl=ja<br>
https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:navigation/navigation-compose/samples/src/main/java/androidx/navigation/compose/samples/NavigationSamples.kt?hl=ja<br>

## about paging

https://github.com/yanzm/UiStateSample<br>

## about Test Framework
https://github.com/android/testing-samples<br>
