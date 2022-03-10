package com.bank.petsignal.shop.api.common.exception

import com.bank.petsignal.shop.api.enums.ErrorCode

class CustomAuthenticationException(
    var error: ErrorCode = ErrorCode.UNAUTHORIZED
) : RuntimeException() {
}