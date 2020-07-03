package com.github.damianw345.swportalbackend.service.impl

import com.github.damianw345.swportalbackend.model.swapi.BaseSwapiResource
import com.github.damianw345.swportalbackend.model.swapi.ResourceType
import com.github.damianw345.swportalbackend.repository.SwapiResourceRepository
import com.github.damianw345.swportalbackend.service.SwapiResourceService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SwapiResourceServiceImpl(private val swapiResourceRepository: SwapiResourceRepository) : SwapiResourceService {

    override fun <T : BaseSwapiResource> getSwapiResourceByTypeAndId(id: Int, resourceType: ResourceType): T? {
        return swapiResourceRepository.getResourceByTypeAndId(id, resourceType)
    }

    override fun <T : BaseSwapiResource> getSwapiPagedResources(pageable: Pageable, resourceType: ResourceType): Page<T> {
        return swapiResourceRepository.getPagedResources(pageable, resourceType)
    }
}
