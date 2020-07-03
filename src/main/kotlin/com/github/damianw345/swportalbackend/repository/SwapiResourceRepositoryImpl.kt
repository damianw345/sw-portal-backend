package com.github.damianw345.swportalbackend.repository

import com.github.damianw345.swportalbackend.model.swapi.BaseSwapiResource
import com.github.damianw345.swportalbackend.model.swapi.ResourceType
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class SwapiResourceRepositoryImpl constructor(val mongoTemplate: MongoTemplate, val mongoOperations: MongoOperations)
    : SwapiResourceRepository {

    override fun <T : BaseSwapiResource> getResourceByTypeAndId(id: Int, resourceType: ResourceType): T? {
        return mongoTemplate.findById(id, resourceType.clazz as Class<T>, resourceType.name)
    }

    override fun <T : BaseSwapiResource> getPagedResources(pageable: Pageable, resourceType: ResourceType): Page<T> {
        val query = Query().with(pageable)
        val list = mongoOperations
                .find(query, resourceType.clazz as Class<T>, resourceType.name)
        val count = mongoOperations.count(Query(), resourceType.clazz, resourceType.name)
        return PageImpl<T>(list, pageable, count)
    }
}
