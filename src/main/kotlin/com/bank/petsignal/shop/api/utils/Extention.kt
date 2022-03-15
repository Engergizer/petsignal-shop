package com.bank.petsignal.shop.api.utils

import com.bank.petsignal.shop.api.common.ApplicationContextProvider
import kotlin.reflect.full.memberProperties

/**
 * Data Class convert to map
 * @param T
 * @return Map<String, Any?>
 */
inline fun <reified T : Any> T.asMap() : Map<String, Any?> {
    val props = T::class.memberProperties.associateBy { it.name }
    return props.keys.associateWith { props[it]?.get(this) }
}

/**
 * Get Bean
 * @params [String]
 * @return T?
 */
fun <T> String.getBean() : T? {
    val applicationContextProvider = ApplicationContextProvider.getApplicationContext()
    return applicationContextProvider?.getBean(this) as T
}