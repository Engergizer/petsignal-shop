package com.bank.petsignal.shop.api.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Token(
    var tokenType: String = "Bearer",
    var accessToken: String? = null,
    var refreshToken: String? = null,
    var accessTokenExpired: Long? = 0,
)