package com.bank.petsignal.shop.api.common

import com.bank.petsignal.shop.api.enums.ErrorCode

class ErrorResponse constructor(
    error: ErrorCode
) : BaseResponse() {
    var errorCode: String = ""
    var errorMessage: String = ""

    init {
        this.errorCode = error.name
        this.errorMessage = error.message
    }
}