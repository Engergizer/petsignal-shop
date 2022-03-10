package com.bank.petsignal.shop.api.common.exception

import com.bank.petsignal.shop.api.enums.ErrorCode

class CustomException(
    val error : ErrorCode = ErrorCode.BAD_REQUEST
) : RuntimeException() {
}