package com.github.damianw345.swportalbackend.repository

import com.github.damianw345.swportalbackend.model.swapi.BaseSwapiResource
import com.github.damianw345.swportalbackend.model.swapi.ResourceType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SwapiResourceRepository {

    fun <T : BaseSwapiResource> findAll(resourceType: ResourceType): List<T>

    fun <T : BaseSwapiResource> getPagedResourceByTypeAndIds(
        resourceType: ResourceType,
        ids: List<Int>,
        pageable: Pageable
    ): Page<T>

    fun <T : BaseSwapiResource> getPagedResources(pageable: Pageable, resourceType: ResourceType): Page<T>
}