package com.horizon.service.serviceapplication.model

// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json            = Json { allowStructuredMapKeys = true }
// val serviceResponse = json.parse(ServiceResponse.serializer(), jsonString)
import kotlinx.serialization.*
@Serializable
data class ServiceResponse (
    val status: String,
    val message: String
)
