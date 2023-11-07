package com.cdg.cdg_news.di

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KtorClient {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClient(Android) {
        install(Logging) {
            logger = CustomHttpLogger()
            level = LogLevel.BODY
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(Json {
                isLenient = false
                ignoreUnknownKeys = true
                allowSpecialFloatingPointValues = true
                useArrayPolymorphism = false
            })
        }

        install(HttpTimeout) {
            requestTimeoutMillis = 1800000L
            connectTimeoutMillis = 120000L
            socketTimeoutMillis = 120000L
        }

    }

    class CustomHttpLogger() : Logger {
        override fun log(message: String) {
            Log.i("loggerTag", message) // Or whatever logging system you want here
        }
    }
}