package com.github.damianw345.swportalbackend.rest.controller

import com.github.damianw345.swportalbackend.service.ResourceService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController("/api")
class ResourceController constructor(val resourceService: ResourceService) {

    @GetMapping("/{resourceType}/{id}")
    fun getResource(@PathVariable("resourceType") resourceType: String,
                    @PathVariable("id") id: Int): String? {

        return resourceService.getResourceByTypeAndId(resourceType, id)
    }
}
