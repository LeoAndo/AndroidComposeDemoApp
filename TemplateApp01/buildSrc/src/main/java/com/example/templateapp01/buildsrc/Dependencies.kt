package com.example.templateapp01.buildsrc

object Versions {
    const val ktLint = "0.43.0"
}

object Libs {
    const val androidGradlePlugin = "com.android.tools.build:gradle:7.0.3"
    const val ktLint = "com.pinterest:ktlint:${Versions.ktLint}"

    object GoogleMaps {
        const val maps = "com.google.android.libraries.maps:maps:3.1.0-beta"
        const val mapsKtx = "com.google.maps.android:maps-v3-ktx:2.2.0"
    }

    object Volley {
        const val volley = "com.android.volley:volley:1.2.0"
    }

    object Accompanist {
        const val version = "0.21.3-beta"
        const val insets = "com.google.accompanist:accompanist-insets:$version"
    }

    object Kotlin {
        private const val version = "1.6.0"
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
        const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val extensions = "org.jetbrains.kotlin:kotlin-android-extensions:$version"

        object Coroutines {
            private const val version = "1.5.2"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }
    }

    object AndroidX {
        object Core {
            const val coreKtx = "androidx.core:core-ktx:1.7.0"
            const val splash = "androidx.core:core-splashscreen:1.0.0-beta01"
        }

        object Activity {
            const val activityCompose = "androidx.activity:activity-compose:1.4.0"
        }

        const val appcompat = "androidx.appcompat:appcompat:1.3.0"

        object Compose {
            const val snapshot = ""
            const val version = "1.2.0-alpha03"

            const val ui = "androidx.compose.ui:ui:1.2.0-alpha03"
            const val runtime = "androidx.compose.runtime:runtime:1.1.0"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:1.1.0"
            const val material = "androidx.compose.material3:material3:1.0.0-alpha05"
            const val foundation = "androidx.compose.foundation:foundation:1.1.0"
            const val layout = "androidx.compose.foundation:foundation-layout:1.1.0"
            const val tooling = "androidx.compose.ui:ui-tooling:1.1.0"
            const val toolingPreview = "androidx.compose.ui:ui-tooling-preview:1.1.0"
            const val animation = "androidx.compose.animation:animation:1.1.0"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:1.1.0"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:1.1.0"
        }

        object Lifecycle {
            private const val version = "2.4.0"
            const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.4.1"
        }

        object Navigation {
            const val navigationCompose = "androidx.navigation:navigation-compose:2.5.0-alpha02"
        }

        object Hilt {
            const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
        }

        object Test {
            private const val version = "1.4.0"
            const val core = "androidx.test:core:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            object Ext {
                private const val version = "1.1.3"
                const val junit = "androidx.test.ext:junit-ktx:$version"
            }

            const val espressoCore = "androidx.test.espresso:espresso-core:3.4.0"
        }
        object Room {
            private const val version = "2.4.1"
            const val runtime = "androidx.room:room-runtime:$version"
            const val compiler = "androidx.room:room-compiler:$version"
            const val roomKtx = "androidx.room:room-ktx:$version"
        }
    }

    object Hilt {
        private const val version = "2.40.5"

        const val gradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
        const val android = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-compiler:$version"
        const val testing = "com.google.dagger:hilt-android-testing:2.39"
    }

    object JUnit {
        private const val version = "4.13.2"
        const val junit = "junit:junit:$version"
    }

    object Coil {
        const val coilCompose = "io.coil-kt:coil-compose:1.3.2"
    }

    object Retrofit {
        private const val version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$version"
        const val moshiAdapter = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object Moshi {
        private const val version = "1.12.0"
        const val moshi = "com.squareup.moshi:moshi:$version"
        const val codegen = "com.squareup.moshi:moshi-kotlin-codegen:1.9.3"
        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$version"
    }
    object OkhHttp3 {
        private const val version = "5.0.0-alpha.2"
        const val okhttp = "com.squareup.okhttp3:okhttp:$version"
        const val loggingInterceptor =  "com.squareup.okhttp3:logging-interceptor:$version"
    }
    object Mockito {
        private const val version = "2.2.0"
        const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:$version"
    }
}

object Urls {
    const val mavenCentralSnapshotRepo = "https://oss.sonatype.org/content/repositories/snapshots/"
    const val composeSnapshotRepo = "https://androidx.dev/snapshots/builds/" +
            "${Libs.AndroidX.Compose.snapshot}/artifacts/repository/"
}
