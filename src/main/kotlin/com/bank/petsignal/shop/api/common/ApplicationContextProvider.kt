package com.bank.petsignal.shop.api.common

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext

import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component


@Component
class ApplicationContextProvider : ApplicationContextAware {
    @Throws(BeansException::class)
    override fun setApplicationContext(ctx: ApplicationContext) {
        applicationContext = ctx
    }

    companion object {
        private var applicationContext: ApplicationContext? = null
        fun getApplicationContext(): ApplicationContext? {
            return applicationContext
        }
    }
}