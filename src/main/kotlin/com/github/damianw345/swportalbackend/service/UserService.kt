package com.github.damianw345.swportalbackend.service

import com.github.damianw345.swportalbackend.dto.UserDto
import com.github.damianw345.swportalbackend.model.security.User

interface UserService {

    fun registerUser(userDto: UserDto): User

    fun loginUser(userDto: UserDto): String
}
