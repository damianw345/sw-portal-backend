package com.github.damianw345.swportalbackend.security

import com.github.damianw345.swportalbackend.exception.ExceptionInfo
import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode.E001
import com.github.damianw345.swportalbackend.util.convertObjectToJson
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

private val logger = KotlinLogging.logger {}

@Component
class RestAuthenticationEntryPoint : BasicAuthenticationEntryPoint() {


    override fun commence(request: HttpServletRequest,
                          response: HttpServletResponse,
                          authException: AuthenticationException) {
        val exceptionInfo = ExceptionInfo.of(E001)

        logger.error(exceptionInfo.toString(), authException)
        setResponse(response, exceptionInfo)
    }

    override fun afterPropertiesSet() {
        realmName = "SwPortal"
        super.afterPropertiesSet()
    }

    private fun setResponse(response: HttpServletResponse, exceptionInfo: ExceptionInfo) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.addHeader("WWW-Authenticate", "Basic realm=$realmName")
        response.writer.write(convertObjectToJson(exceptionInfo))
    }
}
