package com.bank.petsignal.shop.api.controller

import com.bank.petsignal.shop.api.common.BaseResponse
import com.bank.petsignal.shop.api.common.CommonResponse
import com.bank.petsignal.shop.api.service.KakaoService
import com.bank.petsignal.shop.api.service.TokenService
import org.apache.coyote.Response
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping(value = ["/auth"])
class AuthController(
    val tokenService: TokenService,
    val kakaoService: KakaoService,
) {

    companion object {
        private val logger = LoggerFactory.getLogger(this::class.java)
    }

    @GetMapping("/create_token")
    fun createToken() : ResponseEntity<BaseResponse> {
        return ResponseEntity.ok(CommonResponse(tokenService.getToken("dddd")))
    }

    @GetMapping("/sign_in")
    fun signIn() : String = "aaaaaaaaaaaaaaaaaaaaaaaaa"

    @GetMapping("/kakao/callback")
    fun kakaoCallback(@RequestParam("code", required = true) code: String) : RedirectView {
        var accessToken = kakaoService.execKakaoLogin(code)
        logger.info("accessToken => $accessToken")
        return RedirectView("webauthcallback://success?accessToken=${accessToken ?: ""}")
    }

    @PostMapping("/kakao/sign_in")
    fun kakaoSignIn(@RequestParam("accessToken", required = true) accessToken: String) : ResponseEntity<BaseResponse> {
        val user = kakaoService.signIn(accessToken)
        return ResponseEntity.ok(CommonResponse(user))
    }
}