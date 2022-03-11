package com.bank.petsignal.shop.api.config

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
class DBConfig(
    var properties: DataSourceProperties
) {
//    companion object: KLogging()

    @Bean
    @ConfigurationProperties(prefix="spring.datasource")
    fun dataSource() : DataSource {

//        logger.info { "$properties" }

//        logger.info { """
//            properties.driverClassName => ${properties.driverClassName}
//            properties.url => ${properties.url}
//            properties.username => ${properties.username}
//            properties.password => ${properties.password}
//        """.trimIndent() }

        return DataSourceBuilder.create()
            .driverClassName(properties.driverClassName)
            .url(properties.url)
            .username(properties.username)
            .password(properties.password)
            .build()
    }

    @Bean
    fun transactionManager() : PlatformTransactionManager {
        return DataSourceTransactionManager(dataSource())
    }


}