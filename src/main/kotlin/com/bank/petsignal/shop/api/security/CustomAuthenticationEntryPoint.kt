package com.bank.petsignal.shop.api.security

import com.bank.petsignal.shop.api.common.ErrorResponse
import com.bank.petsignal.shop.api.enums.ErrorCode
import com.google.gson.Gson
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response?.let {
            it.status = HttpServletResponse.SC_UNAUTHORIZED
            it.contentType = MediaType.APPLICATION_JSON_VALUE
            it.characterEncoding = StandardCharsets.UTF_8.name()
            it.writer?.write(Gson().toJson(
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse(ErrorCode.UNAUTHORIZED)).body
            ))
        }
    }
}