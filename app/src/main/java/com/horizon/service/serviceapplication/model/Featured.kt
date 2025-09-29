package com.horizon.service.serviceapplication.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Featured(
    val status: String,
    val data: List<FeaturedItemData>,
    val message: String
)

@Parcelize
@Serializable
data class FeaturedItemData(
    val id: String? = null,
    val phone: String? = null,
    @SerialName("response_time")
    val responseTime: String? = null,
    val name: String? = null,
    val description: String? = null,
    val manufact: String? = null,
    val warranty: String? = null,
    val image: String? = null
) : Parcelable

