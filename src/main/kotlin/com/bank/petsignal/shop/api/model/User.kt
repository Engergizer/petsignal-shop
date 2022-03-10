package com.bank.petsignal.shop.api.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class User(
    var id: Int? = 0,
    var shopId: String?,
    var providerId: String?,
    var email: String?,
    var password: String?,
    var username: String?,
    var nickname: String?,
    var phone: String? = null,
    var sex: String? = null,
    var profileImage: String?,
    var socialType: String?,
    var lastLogin: String? = null,
    var updateAt: String? = null,
    var createAt: String? = null,
)