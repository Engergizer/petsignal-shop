package com.bank.petsignal.shop.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
class ShopApiApplication

fun main(args: Array<String>) {
	runApplication<ShopApiApplication>(*args)
}
