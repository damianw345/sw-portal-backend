package com.github.damianw345.swportalbackend.crawler

import com.github.damianw345.swportalbackend.model.Resource
import com.github.damianw345.swportalbackend.rest.client.SwapiClient
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import mu.KotlinLogging
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Component

private val parser = JsonParser()
private val logger = KotlinLogging.logger {}

@Component
class CrawlerImpl constructor(val swapiClient: SwapiClient, val mongoTemplate: MongoTemplate) : Crawler {

    var page = 1

    override fun crawlAndSave(resourceType: String) {
        val pageResultsIterator = PageResultsIterator(resourceType)
        pageResultsIterator.forEachRemaining {
            it.forEach { result ->
                save(resourceType, result)
            }
        }
        page = 1
    }

    private fun save(resourceType: String, result: JsonElement) {
        val resultAsJsonObject = result.asJsonObject
        val resourceIndex = extractResourceIndex(resultAsJsonObject, resourceType)
        val doc = Document.parse(resultAsJsonObject.toString())
        val resource = Resource(resourceIndex, doc)
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

    inner class PageResultsIterator constructor(private val resourceType: String) : Iterator<JsonArray> {

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
