package com.horizon.service.serviceapplication.network

import com.horizon.service.serviceapplication.model.Featured
import com.horizon.service.serviceapplication.model.ServiceRequest
import com.horizon.service.serviceapplication.model.ServiceResponse

interface ApiService {
    suspend fun getItems(): Featured
    suspend fun makeRequest(request: ServiceRequest): ServiceResponse
}