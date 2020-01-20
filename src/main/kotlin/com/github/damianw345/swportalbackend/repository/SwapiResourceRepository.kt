package com.github.damianw345.swportalbackend.repository

import org.bson.Document
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface SwapiResourceRepository {
    fun getResourceByTypeAndId(resourceType: String, id: Int): String?
    fun getPagedResources(pageable: Pageable, resourceType: String): Page<Document>
}
