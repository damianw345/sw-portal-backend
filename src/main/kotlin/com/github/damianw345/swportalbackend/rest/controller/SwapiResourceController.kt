package com.github.damianw345.swportalbackend.rest.controller

import com.github.damianw345.swportalbackend.model.swapi.BaseSwapiResource
import com.github.damianw345.swportalbackend.model.swapi.ResourceType
import com.github.damianw345.swportalbackend.service.SwapiResourceService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/swapi")
class SwapiResourceController<T : BaseSwapiResource> constructor(private val swapiResourceService: SwapiResourceService) {

    @GetMapping("/{resourceType}/{ids}")
    fun getResourceByIdsPage(
        @PathVariable("resourceType") resourceType: String,
        @PathVariable("ids") ids: List<Int>,
        @PageableDefault(size = 10, page = 0) pageable: Pageable
    ): Page<T> {
        return swapiResourceService.getSwapiPagedResources(ResourceType.valueOf(resourceType), ids, pageable)
    }

    @GetMapping("/{resourceType}")
    fun getResourcePage(
        @PathVariable("resourceType") resourceType: String,
        @PageableDefault(size = 10, page = 0) pageable: Pageable
    ): Page<T> {
        return swapiResourceService.getSwapiPagedResources(pageable, ResourceType.valueOf(resourceType.lowercase()))
    }
}
