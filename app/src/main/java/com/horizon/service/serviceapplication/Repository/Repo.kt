package com.horizon.service.serviceapplication.Repository

import com.horizon.service.serviceapplication.model.Featured
import com.horizon.service.serviceapplication.network.ApiService

class Repo(private val api: ApiService) {
    suspend fun getItems(): Featured {
        return api.getItems()
    }
}
