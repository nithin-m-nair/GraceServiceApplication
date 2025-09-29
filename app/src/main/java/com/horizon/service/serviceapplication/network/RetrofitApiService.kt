package com.horizon.service.serviceapplication.network

import com.horizon.service.serviceapplication.model.Featured
import com.horizon.service.serviceapplication.model.ServiceRequest
import com.horizon.service.serviceapplication.model.ServiceResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class RetrofitApiService : ApiService {
    private val api: RetrofitApi

    init {
        // Logging interceptor
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // OkHttp client
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        // Kotlinx Serialization
        val json = Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

        // Retrofit setup
        val retrofit = Retrofit.Builder()
            .baseUrl("https://office.gracecardamomdryer.com/")
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()

        api = retrofit.create(RetrofitApi::class.java)
    }

    override suspend fun getItems(): Featured = api.getItems()
    override suspend fun makeRequest(request: ServiceRequest): ServiceResponse = api.makeRequest(request)
}

