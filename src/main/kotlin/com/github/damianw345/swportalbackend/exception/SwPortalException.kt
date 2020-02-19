package com.github.damianw345.swportalbackend.exception

class SwPortalException(val code: SwPortalExceptionCode, cause: Throwable?)
    : RuntimeException("Code: ${code.name} message: ${code.message}", cause) {

    constructor(code: SwPortalExceptionCode) : this(code, null)
}
