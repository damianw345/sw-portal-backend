package com.github.damianw345.swportalbackend.repository

import com.github.damianw345.swportalbackend.model.swapi.BaseSwapiResource
import com.github.damianw345.swportalbackend.model.swapi.ResourceType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SwapiResourceRepository {

    fun <T : BaseSwapiResource> getPagedResources(pageable: Pageable, resourceType: ResourceType): Page<T>
    fun <T : BaseSwapiResource> getResourceByTypeAndId(id: Int, resourceType: ResourceType): T?
}
