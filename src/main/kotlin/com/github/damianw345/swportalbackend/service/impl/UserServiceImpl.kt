package com.github.damianw345.swportalbackend.service.impl

import com.github.damianw345.swportalbackend.dto.UserDto
import com.github.damianw345.swportalbackend.exception.SwPortalException
import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode.E004
import com.github.damianw345.swportalbackend.model.Role.USER
import com.github.damianw345.swportalbackend.model.User
import com.github.damianw345.swportalbackend.repository.UserRepository
import com.github.damianw345.swportalbackend.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(val userRepository: UserRepository,
                      val passwordEncoder: PasswordEncoder) : UserService {

    override fun registerUser(userDto: UserDto): User {

        if (userRepository.findByUsername(userDto.username) != null)
            throw SwPortalException(E004)

        return userRepository.save(
                User(
                        username = userDto.username,
                        password = passwordEncoder.encode(userDto.password),
                        roles = listOf(USER.name))
        )
    }
}
