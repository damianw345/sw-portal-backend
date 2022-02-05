package com.github.damianw345.swportalbackend.service

import com.github.damianw345.swportalbackend.model.swapi.BaseSwapiResource
import com.github.damianw345.swportalbackend.model.swapi.ResourceType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SwapiResourceService {
    fun <T : BaseSwapiResource> getSwapiPagedResources(
        resourceType: ResourceType,
        ids: List<Int>,
        pageable: Pageable
    ): Page<T>

    fun <T : BaseSwapiResource> getSwapiPagedResources(pageable: Pageable, resourceType: ResourceType): Page<T>
}
