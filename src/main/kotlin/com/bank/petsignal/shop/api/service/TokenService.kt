package com.bank.petsignal.shop.api.service

import com.bank.petsignal.shop.api.model.Token
import com.bank.petsignal.shop.api.security.JwtTokenProvider
import org.springframework.stereotype.Service
import javax.security.auth.Subject

@Service
class TokenService(
    val tokenProvider: JwtTokenProvider
) {

    companion object;

    fun getToken(subject: String) : Token? {
        return tokenProvider.createToken(subject)
    }

}