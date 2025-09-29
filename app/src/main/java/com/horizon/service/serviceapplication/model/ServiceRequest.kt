package com.horizon.service.serviceapplication.model
// To parse the JSON, install kotlin's serialization plugin and do:
//
// val json           = Json { allowStructuredMapKeys = true }
// val serviceRequest = json.parse(ServiceRequest.serializer(), jsonString)
import kotlinx.serialization.*

@Serializable
data class ServiceRequest (
    val phone: String,
    val description: String,
    @SerialName("product_id")
    val productID: String,
    val location: String,
    val date: String,
    val name: String
)
