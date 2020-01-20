package com.github.damianw345.swportalbackend.rest.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin")
class AdminController {

    @GetMapping
    fun adminTest(): String {
        return "admin authorization works!"
    }
}
