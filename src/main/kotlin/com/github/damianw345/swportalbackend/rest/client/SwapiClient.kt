package com.github.damianw345.swportalbackend.rest.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(name = "swapiClient", url = "https://swapi.co/api")
interface SwapiClient {

    @GetMapping(value = ["/{resourceType}"])
    fun fetchResource(@PathVariable("resourceType") resourceType: String,
                      @RequestParam(value = "page", required = false) page: Int = 1
    ): String
}
