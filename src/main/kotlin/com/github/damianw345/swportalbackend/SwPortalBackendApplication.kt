package com.github.damianw345.swportalbackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@EnableMongoRepositories
@SpringBootApplication
class SwPortalBackendApplication

fun main(args: Array<String>) {
    runApplication<SwPortalBackendApplication>(*args)
}
