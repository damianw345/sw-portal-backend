package com.github.damianw345.swportalbackend.service

import org.bson.Document
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SwapiResourceService {
    fun getSwapiResourceByTypeAndId(type: String, id: Int): String?
    fun getSwapiPagedResources(pageable: Pageable, resourceType: String): Page<Document>
}
