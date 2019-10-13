package com.github.damianw345.swportalbackend.service

import org.bson.Document
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ResourceService {
    fun getResourceByTypeAndId(type: String, id: Int): String?
    fun getPagedResources(pageable: Pageable, resourceType: String): Page<Document>
}
