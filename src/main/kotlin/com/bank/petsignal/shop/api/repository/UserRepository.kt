package com.bank.petsignal.shop.api.repository

import com.bank.petsignal.shop.api.enums.SocialType
import com.bank.petsignal.shop.api.model.User
import com.bank.petsignal.shop.api.repository.mapper.UserRowMapper
import com.bank.petsignal.shop.api.utils.asMap
import com.bank.petsignal.shop.api.utils.runDBCatching
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.jdbc.support.KeyHolder
import org.springframework.stereotype.Repository
import org.springframework.transaction.PlatformTransactionManager


@Repository
class UserRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
    val transactionManager: PlatformTransactionManager,
    private val userRowMapper: UserRowMapper,
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }


    /**
     * 유저 조회
     * @param providerId String
     * @return User?
     */
    fun findByProviderIdAndSocialTypeAndEnabled(providerId: String, socialType: SocialType, enabled: Boolean = true) : User? {
        return runDBCatching {
            jdbcTemplate.queryForObject(
                """
            SELECT
                *
            FROM
                employee
            WHERE
                provider_id     = '$providerId'
                AND social_type = '$socialType'
                AND enabled     = $enabled
        """.trimIndent(),
                EmptySqlParameterSource.INSTANCE,
                userRowMapper
            )
        }
    }

    /**
     * 유저 저장
     */
    fun save(user: User) : Long? {
        val params = MapSqlParameterSource()
        val keyHolder: KeyHolder = GeneratedKeyHolder()
        user.asMap().forEach { (key, value) -> params.addValue(key, value)}
        logger.error("params => $params")
        jdbcTemplate.update(
            """
            INSERT INTO employee (
                shop_id
                , provider_id
                , email
                , password
                , username
                , nickname
                , phone
                , sex
                , profile_image
                , social_type
                , create_at
            ) VALUES (
                :shopId
                , :providerId
                , :email
                , :password
                , :username
                , :nickname
                , :phone
                , :sex
                , :profileImage
                , :socialType
                , NOW()
            ) ON DUPLICATE KEY UPDATE
                password = :password
                , nickname = :nickname
                , phone = :phone
                , sex = :sex
                , profile_image = :profileImage
                , enabled = true
            """.trimIndent(),
            params,
            keyHolder
        )
        return keyHolder.keyList[0]["GENERATED_KEY"].toString().toLong()
    }
}