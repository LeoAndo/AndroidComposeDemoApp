package com.example.templateapp01.di

import com.example.templateapp01.BuildConfig
import com.example.templateapp01.data.api.UnsplashService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object ApiModule {
    private val CONTENT_TYPE = "application/json".toMediaType()
    private const val TIMEOUT_SEC: Long = 15

    @Singleton
    @Provides
    fun provideUnsplashService(): UnsplashService {
        val okHttpClientBuilder = createOkHttpClientBuilder().apply {
            addInterceptor(Interceptor { chain ->
                val request = chain.request()
                val requestBuilder = request.newBuilder()

                val originalUrl = request.url.toString()

                if (originalUrl.contains(BuildConfig.UNSPLASH_API_DOMAIN)) {
                    requestBuilder.addHeader("Accept-Version", "v1")
                    requestBuilder.addHeader(
                        "Authorization",
                        "Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}"
                    )
                }
                chain.proceed(requestBuilder.build())
            })
        }
        return Retrofit.Builder()
            .client(okHttpClientBuilder.build())
            .baseUrl(BuildConfig.UNSPLASH_API_DOMAIN)
            .addConverterFactory(MoshiConverterFactory.create(createMoshi()))
            .build()
            .create(UnsplashService::class.java)
    }

    /**
     * https://square.github.io/okhttp/4.x/okhttp/okhttp3/-ok-http-client/#okhttpclients-should-be-shared
     * Performance will be better if you make it a single instance and reuse it in the application.
     */
    private fun createOkHttpClientBuilder(debug: Boolean = BuildConfig.DEBUG): OkHttpClient.Builder {
        val builder = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SEC, TimeUnit.SECONDS)
        if (debug) {
            builder.addInterceptor(createHttpLoggingInterceptor(debug))
        }
        return builder
    }

    private fun createMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    private fun createHttpLoggingInterceptor(debug: Boolean): HttpLoggingInterceptor {
        val httpLoggingInterceptor =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        if (debug) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
        return httpLoggingInterceptor
    }
}