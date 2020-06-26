package com.github.damianw345.swportalbackend.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

enum class SwPortalExceptionCode(val message: String, val httpStatus: HttpStatus) {
    E000("Generic exception", INTERNAL_SERVER_ERROR),
    E001("Unauthorized", UNAUTHORIZED),
    E002("Username not found", NOT_FOUND),
    E003("Incorrect credentials", UNAUTHORIZED),
    E004("Username already exists", CONFLICT),
    E005("Bad request", BAD_REQUEST),
    E006("Forbidden", FORBIDDEN),
    E007("Token expired", FORBIDDEN),
    E008("JWT token is invalid", FORBIDDEN),
    E009("Invalid auth provider", UNPROCESSABLE_ENTITY),
    E010("Invalid redirect URI", BAD_REQUEST),
}
