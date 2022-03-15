package com.bank.petsignal.shop.api.service

import com.bank.petsignal.shop.api.common.exception.CustomException
import com.bank.petsignal.shop.api.enums.ErrorCode
import com.bank.petsignal.shop.api.enums.SocialType
import com.bank.petsignal.shop.api.model.KakaoUser
import com.bank.petsignal.shop.api.model.User
import com.bank.petsignal.shop.api.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Service
class KakaoService(
    private val restTemplate: RestTemplate,
    private val userRepository: UserRepository,
    private val authService: AuthService,
    private val tokenService: TokenService,
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)

        private const val KAKAO_API_KEY = "f6dc0a1da79342d2233eabc1626f8068"
        private const val KAKAO_CALLBACK = "http://10.0.2.2:8090/auth/kakao/callback"
        private const val KAKAO_OAUTH_URL = "https://kauth.kakao.com/oauth/token"
        private const val KAKAO_USER_INFO = "https://kapi.kakao.com/v2/user/me"
    }

    fun execKakaoLogin(authCode: String) : String? {
        val token = getAccessToken(authCode)
        return token?.let {
            it["access_token"].toString()
        }
    }

    fun signIn(accessToken: String) : User {
        var kakoUser = getUserInfo(accessToken)
            ?: throw CustomException(ErrorCode.UNAUTHORIZED_KAKAO)
        logger.info("kakoUser => $kakoUser")
        var user = userRepository.findByProviderIdAndSocialTypeAndEnabled(kakoUser!!.id.toString(), SocialType.KAKAO)
                    ?: authService.kakaoJoin(kakoUser)
        return user.apply {
            this.token = tokenService.getToken(this.email!!)
        }
    }

    private fun getAccessToken(authCode: String) : Map<*, *>? {
        return try {
            val headers = HttpHeaders()
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")

            val params: MultiValueMap<String, String> = LinkedMultiValueMap()
            params.add("grant_type", "authorization_code")
            params.add("client_id", KAKAO_API_KEY)
            params.add("redirect_uri", KAKAO_CALLBACK)
            params.add("code", authCode)

            val kakaoTokenRequest: HttpEntity<MultiValueMap<String, String>> = HttpEntity(params, headers)

            val response = restTemplate.exchange(
                KAKAO_OAUTH_URL,
                HttpMethod.POST,
                kakaoTokenRequest,
                Map::class.java,
                // typeReference<KakaoToken>()
                // object: ParameterizedTypeReference<Map<String, Any>>() {}
            )
            response.body
        } catch(e: Exception) {
            logger.error("KakaoService => getAccessToken => Exception => ${e.message}")
            null
        }
    }

    private fun getUserInfo(accessToken: String) : KakaoUser? {
        return try {
            val headers = HttpHeaders()
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8")
            headers.add("Authorization", "Bearer $accessToken")

            val params: MultiValueMap<String, String> = LinkedMultiValueMap()
            val kakaoTokenRequest: HttpEntity<MultiValueMap<String, String>> = HttpEntity(params, headers)

            val response = restTemplate.exchange(
                KAKAO_USER_INFO,
                HttpMethod.GET,
                kakaoTokenRequest,
                KakaoUser::class.java,
            ).body
            response
        } catch(e : Exception) {
            logger.info("KakaoService => getUserInfo => Exception => $e")
            null
        }
    }
}