package com.bank.petsignal.shop.api.common.exception

import com.bank.petsignal.shop.api.common.BaseResponse
import com.bank.petsignal.shop.api.common.ErrorResponse
import com.bank.petsignal.shop.api.enums.ErrorCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class CustomExceptionAdvice {

    /**
     * 사용자 정의 Exception
     */
    @ExceptionHandler(CustomException::class)
    fun exceptionHandler(request: HttpServletRequest, e: CustomException): ResponseEntity<BaseResponse> {
        return ResponseEntity.status(e.error.status).body(ErrorResponse(e.error))
    }

    /**
     * MissingServletRequestParameterException => 400, BAD_REQUEST
     */
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun exceptionHandler(request: HttpServletRequest, e: MissingServletRequestParameterException) : ResponseEntity<BaseResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(ErrorCode.BAD_REQUEST))
    }

    /**
     * 404 Page Not Found
     */
    @ExceptionHandler(NoHandlerFoundException::class)
    fun exceptionHandler(request: HttpServletRequest, e: NoHandlerFoundException) : ResponseEntity<BaseResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(ErrorCode.PAGE_NOT_FOUND))
    }

    /**
     * 401 사용자 인증 Exception
     */
    @ExceptionHandler(CustomAuthenticationException::class)
    fun exceptionHandler(request: HttpServletRequest, e: CustomAuthenticationException): ResponseEntity<BaseResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse(e.error))
    }

}