package com.bank.petsignal.shop.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
) : WebMvcConfigurer {

    @Bean
    fun dispatcherServlet() : DispatcherServlet {
        val ds = DispatcherServlet()
        ds.setThrowExceptionIfNoHandlerFound(true)
        return ds
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        super.addCorsMappings(registry)
        registry.addMapping("/**").allowedOrigins("*")
    }

}