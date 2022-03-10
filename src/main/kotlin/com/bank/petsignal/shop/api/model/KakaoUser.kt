package com.bank.petsignal.shop.api.model

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class KakaoUser(
    val id: Long,
    val connectedAt: String,
    val properties: KakaoUserProperties,
    val kakaoAccount: KakaoUserAccount
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class KakaoUserProperties(
    val nickname: String,
    val profileImage: String,
    val thumbnailImage: String
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class KakaoUserAccount(
    val profileNicknameNeedsAgreement: Boolean,
    val profileImageNeedsAgreement: Boolean,
    val profile: KakaoUserProfile,
    val hasEmail: Boolean,
    val emailNeedsAgreement: Boolean,
    val isEmailValid: Boolean,
    val isEmailVerified: Boolean,
    val email: String,
    val hasAgeRange: Boolean,
    val ageRangeNeedsAgreement: Boolean,
    val ageRange: String?,
    val hasBirthday: Boolean?,
    val birthdayNeedsAgreement: Boolean?,
    val birthday: String?,
    val birthdayType: String?,
    val hasGender: Boolean?,
    val genderNeedsAgreement: Boolean?,
    val gender: String?,
)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class KakaoUserProfile(
    val thumbnailImageUrl: String,
    val profileImageUrl: String,
    val isDefaultImage: Boolean,
    val nickname: String,
)