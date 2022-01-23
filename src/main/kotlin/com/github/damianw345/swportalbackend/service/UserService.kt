package com.github.damianw345.swportalbackend.service

import com.github.damianw345.swportalbackend.dto.UserDTO
import com.github.damianw345.swportalbackend.model.security.User

interface UserService {

    fun registerUser(userDto: UserDTO): User

    fun loginUser(userDto: UserDTO): String
}
