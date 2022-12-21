package com.digvesh.sampleproject.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("data") val data: Data = Data(),
    @SerializedName("support") val support: Support? = Support()
)