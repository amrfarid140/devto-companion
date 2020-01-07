package me.amryousef.devto.data

import me.amryousef.devto.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("api-key", BuildConfig.API_KEY).build()
        )
    }
}