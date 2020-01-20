package com.github.damianw345.swportalbackend.exception

import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode.E000
import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode.E005
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

data class ExceptionInfo(
        val message: String,
        val code: String,
        val dateTime: String,
        val refNumber: String,
        val details: List<String?> = listOf()
) {
    companion object {
        private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

        fun of(swPortalExceptionCode: SwPortalExceptionCode): ExceptionInfo {
            return ExceptionInfo(
                    swPortalExceptionCode.message,
                    swPortalExceptionCode.name,
                    dateTimeFormatter.format(LocalDateTime.now()),
                    UUID.randomUUID().toString()
            )
        }

        fun ofGenericException(): ExceptionInfo {
            return ExceptionInfo(
                    E000.message,
                    E000.name,
                    dateTimeFormatter.format(LocalDateTime.now()),
                    UUID.randomUUID().toString()
            )
        }

        fun ofBadRequest(details: List<String?>): ExceptionInfo {
            return ExceptionInfo(
                    E005.message,
                    E005.name,
                    dateTimeFormatter.format(LocalDateTime.now()),
                    UUID.randomUUID().toString(),
                    details
            )
        }
    }
}

