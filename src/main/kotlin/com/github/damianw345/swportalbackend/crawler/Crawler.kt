package com.github.damianw345.swportalbackend.crawler

import com.github.damianw345.swportalbackend.model.SwapiResource
import com.github.damianw345.swportalbackend.rest.client.SwapiClient
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import mu.KotlinLogging
import org.bson.Document
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

private val parser = JsonParser()
private val logger = KotlinLogging.logger {}

@Component
@Profile("!test")
class Crawler constructor(val swapiClient: SwapiClient, val mongoTemplate: MongoTemplate)
    : CommandLineRunner {

    private var page = 1

    override fun run(vararg args: String?) {
        crawlIfAnyCollectionNotExists()
    }

    private fun crawlIfAnyCollectionNotExists() {

        val resourceTypes = setOf("people", "planets", "films", "species", "vehicles", "starships")

        if (!mongoTemplate.collectionNames.containsAll(resourceTypes)) {
            logger.info { "Start crawling" }
            resourceTypes.forEach { resourceType ->
                crawlAndSave(resourceType)
            }
            logger.info { "Finished crawling" }
        }
    }

    private fun crawlAndSave(resourceType: String) {
        val pageResultsIterator = PageResultsIterator(resourceType)
        pageResultsIterator.forEachRemaining {
            it.forEach { result -> save(resourceType, result) }
        }
        page = 1
    }

    private fun save(resourceType: String, result: JsonElement) {
        val resultAsJsonObject = result.asJsonObject
        val resourceIndex = extractResourceIndex(resultAsJsonObject, resourceType)
        val doc = Document.parse(resultAsJsonObject.toString())
        val resource = SwapiResource(resourceIndex, doc)
        logger.info { "Saving resource: $resource" }
        mongoTemplate.save(resource, resourceType)
    }

    private fun extractResourceIndex(resultAsJsonObject: JsonObject, resourceType: String): Int {
        return resultAsJsonObject
                .get("url")
                .asJsonPrimitive.asString
                .split("$resourceType/")[1]
                .dropLast(1)//remove trailing slash
                .toInt()
    }

    private inner class PageResultsIterator constructor(private val resourceType: String) : Iterator<JsonArray> {

        private var results = JsonArray()

        override fun hasNext(): Boolean {
            val json = swapiClient.fetchResource(resourceType, page)
            val jsonTree: JsonElement = parser.parse(json)
            val currentRoot = jsonTree.asJsonObject
            results = currentRoot.get("results").asJsonArray

            if (currentRoot.get("next").isJsonNull) {
                results.forEach { save(resourceType, it) }
            }
            return !currentRoot.get("next").isJsonNull
        }

        override fun next(): JsonArray {
            page++
            return results
        }
    }
}
