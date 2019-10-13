package com.github.damianw345.swportalbackend.repository

import com.github.damianw345.swportalbackend.model.Resource
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository

@Repository
class ResourceRepositoryImpl constructor(val mongoTemplate: MongoTemplate) : ResourceRepository {

    override fun getResourceByTypeAndId(type: String, id: Int): String? {
        return mongoTemplate.findById(id, Resource::class.java, type)?.body?.toJson()
    }
}
