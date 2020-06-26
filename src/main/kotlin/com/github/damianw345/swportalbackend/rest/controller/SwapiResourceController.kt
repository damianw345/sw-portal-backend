package com.github.damianw345.swportalbackend.rest.controller

import com.github.damianw345.swportalbackend.service.SwapiResourceService
import org.bson.Document
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedResources
import org.springframework.hateoas.Resource
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/swapi")
class SwapiResourceController constructor(private val swapiResourceService: SwapiResourceService) {

    @GetMapping("/{resourceType}/{id}")
    fun getResource(@PathVariable("resourceType") resourceType: String,
                    @PathVariable("id") id: Int): String? {

        return swapiResourceService.getSwapiResourceByTypeAndId(resourceType, id)
    }

    @GetMapping("/{resourceType}")
    fun getResourcePage(
            @PathVariable("resourceType") resourceType: String,
            @PageableDefault(size = 10, page = 0) pageable: Pageable,
            pagedResourcesAssembler: PagedResourcesAssembler<Document>
    ): ResponseEntity<PagedResources<Resource<Document>>> {

        val link = linkTo(methodOn(SwapiResourceController::class.java)
                .getResourcePage(resourceType, pageable, pagedResourcesAssembler))
                .withSelfRel()
        val pagedModel = swapiResourceService.getSwapiPagedResources(pageable, resourceType)
        return ResponseEntity
                .ok()
                .body(pagedResourcesAssembler.toResource(pagedModel, link))
    }
}
