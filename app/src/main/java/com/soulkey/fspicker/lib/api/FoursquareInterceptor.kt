package com.soulkey.fspicker.lib.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

open class FoursquareInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("Accept-Language", "ko")
                .url(chain.request().url)
                .build()
        )
    }
}