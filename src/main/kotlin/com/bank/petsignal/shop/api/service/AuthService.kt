package com.bank.petsignal.shop.api.service

import com.bank.petsignal.shop.api.enums.SexType
import com.bank.petsignal.shop.api.enums.SocialType
import com.bank.petsignal.shop.api.model.KakaoUser
import com.bank.petsignal.shop.api.model.User
import com.bank.petsignal.shop.api.repository.UserRepository
import com.bank.petsignal.shop.api.utils.ProviderConstants
import com.bank.petsignal.shop.api.utils.runDBCatch
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun kakaoJoin(kakaoUser: KakaoUser) : User? {
        return createUser(
            providerId = kakaoUser.id,
            email = kakaoUser.kakaoAccount.email,
            username = listOf(ProviderConstants.KAKAO, kakaoUser.id.toString()).joinToString(""),
            password = listOf(ProviderConstants.KAKAO, kakaoUser.id.toString()).joinToString(""),
            nickname = kakaoUser.properties.nickname,
            profileImage = kakaoUser.properties.profileImage,
            socialType = SocialType.KAKAO,
            sexType = SexType.valueOf(kakaoUser.kakaoAccount.gender?.uppercase() ?: SexType.UNKNOWN.name),
        )
    }

    fun createUser(
        providerId: Long, email: String, profileImage: String, nickname: String, socialType: SocialType, sexType: SexType, username: String, password: String
    ) : User? {

        val user = User(
            shopId = "PS00000000",
            providerId = providerId.toString(),
            email = email,
            username = username,
            nickname = nickname,
            password = passwordEncoder.encode(password),
            profileImage = profileImage,
            socialType = socialType.name,
            sex = sexType.name,
        )
        val result = userRepository.save(user)
        logger.info("result => $result")
        return null
    }
}