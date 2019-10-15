package com.github.damianw345.swportalbackend.rest.controller

import com.github.damianw345.swportalbackend.service.ResourceService
import org.bson.Document
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedResources
import org.springframework.hateoas.Resource
import org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo
import org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = ["\${frontend.url}"])
class ResourceController constructor(val resourceService: ResourceService) {

    @GetMapping("/{resourceType}/{id}")
    fun getResource(@PathVariable("resourceType") resourceType: String,
                    @PathVariable("id") id: Int): String? {

        return resourceService.getResourceByTypeAndId(resourceType, id)
    }

    @GetMapping("/{resourceType}")
    fun getResourcePage(
            @PathVariable("resourceType") resourceType: String,
            @PageableDefault(size = 10, page = 0) pageable: Pageable,
            pagedResourcesAssembler: PagedResourcesAssembler<Document>
    ): ResponseEntity<PagedResources<Resource<Document>>> {

        val link = linkTo(methodOn(ResourceController::class.java)
                .getResourcePage(resourceType, pageable, pagedResourcesAssembler))
                .withSelfRel()
        val pagedResources = resourceService.getPagedResources(pageable, resourceType)
        return ResponseEntity
                .ok()
                .body(pagedResourcesAssembler.toResource(pagedResources, link))
    }
}
