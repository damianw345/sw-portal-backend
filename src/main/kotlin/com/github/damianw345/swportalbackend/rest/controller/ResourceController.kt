package com.github.damianw345.swportalbackend.rest.controller

import com.github.damianw345.swportalbackend.service.ResourceService
import org.bson.Document
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ResourceController constructor(val resourceService: ResourceService) {

    @GetMapping("/{resourceType}/{id}")
    fun getResource(@PathVariable("resourceType") resourceType: String,
                    @PathVariable("id") id: Int): String? {

        return resourceService.getResourceByTypeAndId(resourceType, id)
    }

    @GetMapping("/{resourceType}")
    fun getResourcePage(
            @PathVariable("resourceType") resourceType: String,
            @PageableDefault(size = 10, page = 0) pageable: Pageable
    ): Page<Document> {
        return resourceService.getPagedResources(pageable, resourceType)
    }
}
