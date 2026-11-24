package com.duoc.app.data.remote.client

import com.duoc.app.data.repository.SesionManager
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val sesionManager: SesionManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (request.url.encodedPath.endsWith("/auth/login")) {
            return chain.proceed(request)
        }

        val token = sesionManager.obtenerToken()
        val authedRequest = if (!token.isNullOrBlank()) {
            request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        } else {
            request
        }
        return chain.proceed(authedRequest)
    }
}