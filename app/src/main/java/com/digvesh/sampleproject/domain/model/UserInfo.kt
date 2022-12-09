package com.digvesh.sampleproject.domain.model

data class UserInfo(
    var id: Int? = -1,
    var firstName: String? = "",
    var lastName: String? = "",
    var displayName: String? = "",
    var email: String? = "",
    var avatarImage: String? = "",
)
