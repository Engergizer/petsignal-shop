package com.bank.petsignal.shop.api.repository.mapper

import com.bank.petsignal.shop.api.model.User
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class UserRowMapper : RowMapper<User> {
    override fun mapRow(rs: ResultSet, rowNum: Int): User {
        return User(
            rs.getLong("id"),
            rs.getString("shop_id"),
            rs.getString("provider_id"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("username"),
            rs.getString("nickname"),
            rs.getString("phone"),
            rs.getString("sex"),
            rs.getString("profile_image"),
            rs.getString("social_type"),
            rs.getString("last_login"),
            rs.getString("update_at"),
            rs.getString("create_at"),
        )
    }
}