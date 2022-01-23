package com.github.damianw345.swportalbackend.rest.controller

import com.github.damianw345.swportalbackend.dto.UserDTO
import com.github.damianw345.swportalbackend.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    fun registerUser(@RequestBody @Valid userDto: UserDTO): UserDTO {
        return userService.registerUser(userDto).let { UserDTO(it.username, it.roles) }
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    fun loginUser(@RequestBody @Valid userDto: UserDTO): Map<String, String> {
        return mapOf("token" to userService.loginUser(userDto))
    }
}
