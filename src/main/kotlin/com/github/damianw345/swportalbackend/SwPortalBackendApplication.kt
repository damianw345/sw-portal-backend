package com.github.damianw345.swportalbackend

import com.github.damianw345.swportalbackend.crawler.Crawler
import mu.KotlinLogging
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

private val logger = KotlinLogging.logger {}

@EnableFeignClients
@EnableMongoRepositories
@SpringBootApplication
class SwPortalBackendApplication constructor(val crawler: Crawler,
                                             val mongoTemplate: MongoTemplate) : CommandLineRunner {

    override fun run(vararg args: String?) {
        crawlIfAnyCollectionNotExists()
    }

    private fun crawlIfAnyCollectionNotExists() {

        val resourceTypes = setOf("people", "planets", "films", "species", "vehicles", "starships")

        if (mongoTemplate.collectionNames != resourceTypes) {
            logger.info { "Start crawling" }
            resourceTypes.forEach { resourceType ->
                crawler.crawlAndSave(resourceType)
            }
            logger.info { "Finished crawling" }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<SwPortalBackendApplication>(*args)
}
