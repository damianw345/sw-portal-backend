package com.github.damianw345.swportalbackend.security

import com.github.damianw345.swportalbackend.exception.ExceptionInfo
import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode
import com.github.damianw345.swportalbackend.util.convertObjectToJson
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val logger = KotlinLogging.logger {}

class RestAccessDeniedHandler : AccessDeniedHandler {
    override fun handle(request: HttpServletRequest,
                        response: HttpServletResponse,
                        accessDeniedException: AccessDeniedException) {

        val exceptionInfo = ExceptionInfo.of(SwPortalExceptionCode.E006)

        logger.error(exceptionInfo.toString(), accessDeniedException)
        setResponse(response, exceptionInfo)
    }

    private fun setResponse(response: HttpServletResponse, exceptionInfo: ExceptionInfo) {
        response.status = HttpStatus.FORBIDDEN.value()
        response.writer.write(convertObjectToJson(exceptionInfo))
    }
}
