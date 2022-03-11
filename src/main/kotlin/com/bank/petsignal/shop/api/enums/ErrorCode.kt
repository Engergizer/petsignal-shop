package com.bank.petsignal.shop.api.enums

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String,
) {
    /**
     * 400 BAD_REQUEST: 잘못된 요청
     */
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    /**
     * 401 UNAUTHORIZED: 비인증
     */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"인증 정보가 존재하지 않습니다."),

    /**
     * 401 UNAUTHORIZED: 카카오 로그인 실패
     */
    UNAUTHORIZED_KAKAO(HttpStatus.UNAUTHORIZED, "카카오 유저 정보 조회 에러"),

    /**
     * 403 FORBIDDEN: 인증 정보 불일치 접근 제한
     */
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "인증 정보가 유효하지 않습니다."),

    /**
     * 404 NOT_FOUND: 리소스를 찾을 수 없음
     */
    PAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "요청하신 페이지를 찾을 수 없습니다."),

    /**
     * 405 METHOD_NOT_ALLOWED: 허용되지 않은 Request Method 호출
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "잘못된 접근 요청입니다."),

    /**
     * 500 INTERNAL_SERVER_ERROR: 내부 서버 오류
     */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "요청에 실패하였습니다.\n내부 서버 오류"),

//    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버와의 통신이 원할하지 않습니다"),
//    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "Input is invalid value"),
//    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "invalid type value"),
//    NOT_FOUND(HttpStatus.NOT_FOUND, "Not found"),
//    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Method type is invalid"),
//    EXIST_USER(HttpStatus.BAD_REQUEST, "이미 가입한 유저입니다."),
//    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
//    UNAUTHORIZED_KAKAO(HttpStatus.UNAUTHORIZED, "카카오 로그인에 실패하였습니다.")
}