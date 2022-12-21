package com.digvesh.sampleproject.domain.model

data class UserInfo(
    val id: Int? = -1,
    val firstName: String? = "",
    val lastName: String? = "",
    val displayName: String? = "",
    val email: String? = "",
    val avatarImage: String? = "",
)
