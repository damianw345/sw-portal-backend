package com.github.damianw345.swportalbackend.service.impl

import com.github.damianw345.swportalbackend.repository.SwapiResourceRepository
import com.github.damianw345.swportalbackend.service.SwapiResourceService
import org.bson.Document
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SwapiResourceServiceImpl(private val swapiResourceRepository: SwapiResourceRepository) : SwapiResourceService {

    override fun getSwapiResourceByTypeAndId(type: String, id: Int): String? {
        return swapiResourceRepository.getResourceByTypeAndId(type, id)
    }

    override fun getSwapiPagedResources(pageable: Pageable, resourceType: String): Page<Document> {
        return swapiResourceRepository.getPagedResources(pageable, resourceType)
    }
}
