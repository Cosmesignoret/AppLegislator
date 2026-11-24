package com.duoc.app.data.remote.client

import android.content.Context
import com.duoc.app.data.remote.api.AuthApi
import com.duoc.app.data.remote.api.DocumentApi
import com.duoc.app.data.remote.api.LegislativeChangeApi
import com.duoc.app.data.remote.api.NoteApi
import com.duoc.app.data.repository.SesionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient(context: Context) {

    private val baseUrl = "http://3.81.226.195:8080/"
    private val sesionManager = SesionManager(context)
    private val tokenInterceptor = TokenInterceptor(sesionManager)

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(tokenInterceptor)
        .addInterceptor(logging)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val documentApi: DocumentApi = retrofit.create(DocumentApi::class.java)
    val changeApi: LegislativeChangeApi = retrofit.create(LegislativeChangeApi::class.java)
    val authApi: AuthApi = retrofit.create(AuthApi::class.java)
    val noteApi: NoteApi = retrofit.create(NoteApi::class.java)
}