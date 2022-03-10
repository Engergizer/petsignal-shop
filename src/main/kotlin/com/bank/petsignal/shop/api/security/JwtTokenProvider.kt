package com.bank.petsignal.shop.api.security

import com.bank.petsignal.shop.api.common.exception.CustomAuthenticationException
import com.bank.petsignal.shop.api.model.Token
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*
import java.util.function.Function

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.issuer}") private val issuer: String,
    @Value("\${jwt.audience}") private val audience: String,
    @Value("\${jwt.access-expiration}") private val accessExpiration: Long,
    @Value("\${jwt.refresh-expiration}") private val refreshExpiration: Long,
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private val algorithm: SignatureAlgorithm = SignatureAlgorithm.HS256
    }

    private fun getsecretKey(): Key {
        val bytes = secretKey.toByteArray(StandardCharsets.UTF_8)
        return Keys.hmacShaKeyFor(bytes)
    }

    fun getUsernameFromToken(token: String): String {
        return getClaimFromToken(token) { obj: Claims -> obj.subject }
    }

    fun getIssuedAtDateFromToken(token: String): Date {
        return getClaimFromToken(token) { obj: Claims -> obj.issuedAt }
    }

    fun getExpirationDateFromToken(token: String): Date {
        return getClaimFromToken(token) { obj: Claims -> obj.expiration }
    }

    fun getIssuerFromToken(token: String): String {
        return getClaimFromToken(token) { obj: Claims -> obj.issuer }
    }

    fun getAudienceFromToken(token: String): String {
        return getClaimFromToken(token) { obj: Claims -> obj.audience }
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    private fun ignoreTokenExpiration(token: String): Boolean {
        return audience == getAudienceFromToken(token)
    }

    fun validateToken(token: String): Boolean {
        return (!this.isTokenExpired(token) && ignoreTokenExpiration(token))
    }

    fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims: Claims = getClaimsFromToken(token)
        return claims.let { claimsResolver.apply(claims) }
    }

    private fun getClaimsFromToken(token: String) : Claims {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(getsecretKey())
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: ExpiredJwtException) {
            logger.error("JwtTokenProvider => getClaimsFromToken => ExpiredJwtException => ${e.message}")
            e.claims
        } catch (e: Exception) {
            logger.error("JwtTokenProvider => getClaimsFromToken => Exception => ${e.message}")
            throw CustomAuthenticationException()
        }
    }

    private fun calculateExpirationDate(createdDate: Date, expiration: Long): Date {
        return Date(createdDate.time + expiration * 1000)
    }

    fun getAuthentication(token: String) : Authentication? {
        return try {
            val authorities = ArrayList<GrantedAuthority>()
            authorities.add(SimpleGrantedAuthority("ROLE_SHOP"))

            val username = getUsernameFromToken(token)
            val principal = User(username, "", authorities)
            UsernamePasswordAuthenticationToken(principal, null, authorities)
        } catch (e: Exception) {
            logger.error("getAuthentication => Exception => ${e.message}")
            null
        }
    }

    fun createToken(subject: String) : Token? {
        val claims = Jwts.claims().setSubject(subject)
        val now = Date()

        val accessToken = Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setClaims(claims)
            .setIssuer(issuer)
            .setAudience(audience)
            .setIssuedAt(now)
            .setExpiration(calculateExpirationDate(now, accessExpiration))
            .signWith(getsecretKey(), algorithm)
            .compact()

        val refreshToken = Jwts.builder()
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setExpiration(calculateExpirationDate(now, refreshExpiration))
            .signWith(getsecretKey(), algorithm)
            .compact()

        return Token("Bearer").apply {
            this.accessToken = accessToken
            this.refreshToken = refreshToken
            this.accessTokenExpired = accessExpiration
        }
    }

}