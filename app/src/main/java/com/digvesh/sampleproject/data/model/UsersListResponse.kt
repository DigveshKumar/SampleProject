package com.digvesh.sampleproject.data.model

import com.google.gson.annotations.SerializedName

data class UsersListResponse(
    @SerializedName("page") val page: Int? = null,
    @SerializedName("per_page") val perPage: Int? = null,
    @SerializedName("total") val total: Int? = null,
    @SerializedName("total_pages") val totalPages: Int? = null,
    @SerializedName("data") val data: List<Data> = arrayListOf(),
    @SerializedName("support") val support: Support? = Support()

)