package com.github.damianw345.swportalbackend.util

import mu.KotlinLogging
import java.net.InetAddress
import java.net.UnknownHostException

private val logger = KotlinLogging.logger {}

fun hostName(): String {
    try {
        return InetAddress.getLocalHost().hostName
    } catch (ex: UnknownHostException) {
        logger.error(ex) { "Unknown host: $ex" }
    }
    return "unknown"
}
