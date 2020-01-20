package com.github.damianw345.swportalbackend.exception

class SwPortalException(val code: SwPortalExceptionCode) : RuntimeException("Code: ${code.name} message: ${code.message}")
