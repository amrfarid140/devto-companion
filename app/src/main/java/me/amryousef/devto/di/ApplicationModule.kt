package me.amryousef.devto.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import me.amryousef.devto.data.URIAdapter
import org.koin.dsl.module

val applicationModule = module {
    single { buildMoshi() }
}

private fun buildMoshi() =
    Moshi.Builder()
        .add(URIAdapter)
        .add(Rfc3339DateJsonAdapter())
        .build()