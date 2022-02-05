package com.github.damianw345.swportalbackend.repository

import com.github.damianw345.swportalbackend.model.swapi.BaseSwapiResource
import com.github.damianw345.swportalbackend.model.swapi.ResourceType
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class SwapiResourceRepositoryImpl constructor(val mongoTemplate: MongoTemplate, val mongoOperations: MongoOperations) :
    SwapiResourceRepository {

    override fun <T : BaseSwapiResource> findAll(resourceType: ResourceType): List<T> {
        return mongoTemplate.findAll(
            resourceType.clazz as Class<T>,
            resourceType.name
        )
    }

    override fun <T : BaseSwapiResource> getPagedResourceByTypeAndIds(
        resourceType: ResourceType,
        ids: List<Int>,
        pageable: Pageable
    ): Page<T> {
        val query = Query().with(pageable)
        query.addCriteria(Criteria.where("_id").`in`(ids))
        return getPagedResourcesBy<T>(query, resourceType, pageable)
    }

    override fun <T : BaseSwapiResource> getPagedResources(
        pageable: Pageable,
        resourceType: ResourceType
    ): Page<T> {
        val query = Query().with(pageable)
        return getPagedResourcesBy<T>(query, resourceType, pageable)
    }

    private fun <T : BaseSwapiResource> getPagedResourcesBy(
        query: Query,
        resourceType: ResourceType,
        pageable: Pageable
    ): PageImpl<T> {
        val list = mongoOperations
            .find(query, resourceType.clazz as Class<T>, resourceType.name)
        val count = mongoOperations.count(Query(), resourceType.clazz, resourceType.name)
        return PageImpl(list, pageable, count)
    }
}
