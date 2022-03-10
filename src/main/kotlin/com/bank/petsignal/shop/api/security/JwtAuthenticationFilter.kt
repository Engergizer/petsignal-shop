package com.bank.petsignal.shop.api.security

import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestHeader: String? = request.getHeader("Authorization")

        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {

            val token = requestHeader.substring(7)
            val username = jwtTokenProvider.getUsernameFromToken(token)

            logger.info("token => $token")
            logger.info("username => $username")
            logger.info("expiredAt => ${jwtTokenProvider.getExpirationDateFromToken(token)}")
            logger.info("issuerAt => ${jwtTokenProvider.getIssuedAtDateFromToken(token)}")
            logger.info("issuer => ${jwtTokenProvider.getIssuerFromToken(token)}")
            logger.info("audience => ${jwtTokenProvider.getAudienceFromToken(token)}")

            logger.info("username.isNullOrEmpty() => ${username.isEmpty()}")
            logger.info("jwtTokenProvider.validateToken(token) => ${jwtTokenProvider.validateToken(token)}")

            if (username.isNotEmpty() && jwtTokenProvider.validateToken(token)) {
               jwtTokenProvider.getAuthentication(token)?.let {
                   SecurityContextHolder.getContext().authentication = it

                   filterChain.doFilter(request, response)
               }
            }
        }

        filterChain.doFilter(request, response)
    }
}