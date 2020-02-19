package com.github.damianw345.swportalbackend.rest.controller

import com.github.damianw345.swportalbackend.dto.UserDto
import com.github.damianw345.swportalbackend.security.TOKEN_PREFIX
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
@CrossOrigin(origins = ["\${app-config.frontend.url}"])
class UserController(val userService: UserService) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(@RequestBody @Valid userDto: UserDto): UserDto {
        return userService.registerUser(userDto).let { UserDto(it.username, it.roles) }
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun loginUser(@RequestBody @Valid userDto: UserDto): Map<String, String> {
        return mapOf("token" to userService.loginUser(userDto))
    }
}
