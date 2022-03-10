package com.bank.petsignal.shop.api.repository

import com.bank.petsignal.shop.api.common.exception.CustomException
import com.bank.petsignal.shop.api.enums.ErrorCode
import com.bank.petsignal.shop.api.model.User
import com.bank.petsignal.shop.api.repository.mapper.UserRowMapper
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    private val userRowMapper: UserRowMapper,
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    fun findByProviderId(providerId: String) : User? {
        logger.info("providerId => $providerId")
        return try {
            jdbcTemplate.queryForObject(
                """
                    SELECT
                        *                    
                    FROM 
                        employee
                    WHERE 
                        provider_id = :providerId
                        AND enabled = 1
                """.trimIndent(),
                Collections.singletonMap("providerId", providerId),
                userRowMapper
            )
        } catch (e: Exception) {
            when(e) {
                is EmptyResultDataAccessException -> null
                else -> throw CustomException(ErrorCode.INTERNAL_SERVER_ERROR)
            }
        }
    }
}