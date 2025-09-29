package com.horizon.service.serviceapplication.network

import com.horizon.service.serviceapplication.model.Featured
import com.horizon.service.serviceapplication.model.ServiceRequest
import com.horizon.service.serviceapplication.model.ServiceResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitApi {
    @GET("UserApi/get_featured_products")
    suspend fun getItems(): Featured
    @POST("UserApi/service_request")
    suspend fun makeRequest(@Body request: ServiceRequest): ServiceResponse

}
