package com.digvesh.sampleproject.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("data") var data: Data = Data(),
    @SerializedName("support") var support: Support? = Support()
)