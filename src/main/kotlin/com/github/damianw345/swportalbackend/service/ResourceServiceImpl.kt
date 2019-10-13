package com.github.damianw345.swportalbackend.service

import com.github.damianw345.swportalbackend.repository.ResourceRepository
import org.bson.Document
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ResourceServiceImpl constructor(val resourceRepository: ResourceRepository) : ResourceService {

    override fun getResourceByTypeAndId(type: String, id: Int): String? {
        return resourceRepository.getResourceByTypeAndId(type, id)
    }

    override fun getPagedResources(pageable: Pageable, resourceType: String): Page<Document> {
        return resourceRepository.getPagedResources(pageable, resourceType)
    }
}
