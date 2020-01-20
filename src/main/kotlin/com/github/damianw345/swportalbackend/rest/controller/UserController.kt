package com.github.damianw345.swportalbackend.rest.controller

import com.github.damianw345.swportalbackend.dto.UserDto
import com.github.damianw345.swportalbackend.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = ["\${frontend.url}"])
class UserController(val userService: UserService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(@RequestBody @Valid userDto: UserDto): UserDto {
        return userService.registerUser(userDto).let { UserDto(username = it.username, roles = it.roles, password = "") }
    }
}
