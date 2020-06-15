package com.github.damianw345.swportalbackend.exception

import com.github.damianw345.swportalbackend.util.hostName
import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.context.request.ServletWebRequest
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


private val logger = KotlinLogging.logger {}

@ControllerAdvice
class SwPortalExceptionHandler : ResponseEntityExceptionHandler() {

    @ResponseBody
    @ExceptionHandler(value = [Exception::class])
    fun handleApplicationException(exception: Exception, request: ServletWebRequest): ResponseEntity<Any> {

        logger.error("Exception occurred on host: ${hostName()} " +
                "when accessing method: ${request.httpMethod} at URI: ${request.request.requestURI}", exception)

        return when (exception) {
            is SwPortalException -> ResponseEntity(ExceptionInfo.of(exception.code), exception.code.httpStatus)
            else -> ResponseEntity(ExceptionInfo.ofGenericException(), INTERNAL_SERVER_ERROR)
        }
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException,
                                              headers: HttpHeaders,
                                              status: HttpStatus,
                                              request: WebRequest): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors
                .map { it.field + ": " + it.defaultMessage }
                .union(ex.bindingResult.globalErrors.map { it.objectName + ": " + it.defaultMessage })
                .toList()

        return ResponseEntity(ExceptionInfo.ofBadRequest(errors), BAD_REQUEST)
    }

    override fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException,
                                              headers: HttpHeaders,
                                              status: HttpStatus,
                                              request: WebRequest): ResponseEntity<Any> {
        return ResponseEntity(ExceptionInfo.ofBadRequest(listOf(ex.message)), BAD_REQUEST)
    }
}
