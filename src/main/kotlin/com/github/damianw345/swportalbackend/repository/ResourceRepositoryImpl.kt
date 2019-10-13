package com.github.damianw345.swportalbackend.repository

import com.github.damianw345.swportalbackend.model.Resource
import org.bson.Document
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository


@Repository
class ResourceRepositoryImpl constructor(val mongoTemplate: MongoTemplate, val mongoOperations: MongoOperations) : ResourceRepository {

    override fun getResourceByTypeAndId(resourceType: String, id: Int): String? {
        return mongoTemplate.findById(id, Resource::class.java, resourceType)?.body?.toJson()
    }

    override fun getPagedResources(pageable: Pageable, resourceType: String): Page<Document> {
        val query = Query().with(pageable)
        val list = mongoOperations
                .find(query, Resource::class.java, resourceType)
                .map { it.body }
        val count = mongoOperations.count(query, Resource::class.java, resourceType)
        return PageImpl<Document>(list, pageable, count)
    }
}
