package com.github.damianw345.swportalbackend.service

import com.github.damianw345.swportalbackend.repository.ResourceRepository
import org.springframework.stereotype.Service

@Service
class ResourceServiceImpl constructor(val resourceRepository: ResourceRepository) : ResourceService {

    override fun getResourceByTypeAndId(type: String, id: Int): String? {
        return resourceRepository.getResourceByTypeAndId(type, id)
    }
}
