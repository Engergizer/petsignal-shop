package com.bank.petsignal.shop.api.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/shop"])
class ShopController {

    @GetMapping("/list")
    fun list(): String = "bbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
}