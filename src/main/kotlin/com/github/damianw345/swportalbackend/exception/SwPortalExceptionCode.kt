package com.github.damianw345.swportalbackend.exception

enum class SwPortalExceptionCode(val message: String) {
    E000("Generic exception"),
    E001("Unauthorized"),
    E002("Username not found"),
    E003("Incorrect credentials"),
    E004("Username already exists"),
    E005("Bad request"),
    E006("Forbidden"),
    E007("Token expired"),
    E008("JWT token is invalid"),
}
