package com.bank.petsignal.shop.api.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class User(
    var id: Long? = 0,
    var shopId: String? = null,
    var providerId: String? = null,
    var email: String? = null,
    var password: String? = null,
    var username: String? = null,
    var nickname: String? = null,
    var phone: String? = null,
    var sex: String? = null,
    var profileImage: String? = null,
    var socialType: String? = null,
    var lastLogin: String? = null,
    var updateAt: String? = null,
    var createAt: String? = null,
    var token: Token? = null
)