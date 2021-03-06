package com.bank.petsignal.shop.api.common

class CommonResponse<T>(
    val data: T
) : BaseResponse() {
    var totalPage: Int? = 0
    var totalRecord: Int? = 0

    constructor(data: T, totalPage: Int) : this(data) {
        this.totalPage = totalPage
    }

    constructor(data: T, totalPage: Int, totalRecord: Int) : this(data) {
        this.totalPage = totalPage
        this.totalRecord = totalRecord
    }
}