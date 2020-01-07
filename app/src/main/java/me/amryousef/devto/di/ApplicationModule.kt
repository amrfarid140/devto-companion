package me.amryousef.devto.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import io.reactivex.internal.schedulers.IoScheduler
import me.amryousef.devto.data.ApiService
import me.amryousef.devto.data.AuthHeaderInterceptor
import me.amryousef.devto.data.URIAdapter
import me.amryousef.devto.presentation.ArticlesStateReducer
import me.amryousef.devto.presentation.ArticlesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

private val moshi =
    Moshi.Builder()
        .add(URIAdapter)
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()

private val retrofit = Retrofit.Builder()
    .client(
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BASIC) })
            .addInterceptor(AuthHeaderInterceptor()).build()
    )
    .baseUrl("https://dev.to/api/")
    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(IoScheduler()))
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

val applicationModule = module {
    single { moshi }
    single { retrofit }
    single { retrofit.create(ApiService::class.java) }
    factory { ArticlesStateReducer() }
    viewModel { ArticlesViewModel(get(), get()) }
}
