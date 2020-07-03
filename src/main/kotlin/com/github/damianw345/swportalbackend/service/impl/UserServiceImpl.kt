package com.github.damianw345.swportalbackend.service.impl

import com.github.damianw345.swportalbackend.dto.UserDto
import com.github.damianw345.swportalbackend.exception.SwPortalException
import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode
import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode.E004
import com.github.damianw345.swportalbackend.model.security.Role.ROLE_USER
import com.github.damianw345.swportalbackend.model.security.User
import com.github.damianw345.swportalbackend.repository.UserRepository
import com.github.damianw345.swportalbackend.security.AuthProvider
import com.github.damianw345.swportalbackend.service.UserService
import com.github.damianw345.swportalbackend.util.JwtUtil
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository,
                      private val jwtUtil: JwtUtil,
                      private val passwordEncoder: PasswordEncoder) : UserService {

    override fun registerUser(userDto: UserDto): User {

        if (userRepository.findByUsername(userDto.username) != null)
            throw SwPortalException(E004)

        return userRepository.save(
                User(
                        username = userDto.username,
                        password = passwordEncoder.encode(userDto.password),
                        roles = listOf(ROLE_USER.name),
                        authProvider = AuthProvider.local,
                        attributes = emptyMap()
                )
        )
    }

    override fun loginUser(userDto: UserDto): String {
        return userRepository.findByUsername(userDto.username)
                ?.takeIf { passwordEncoder.matches(userDto.password, it.password) }
                ?.let { jwtUtil.buildToken(it.username, it.roles) }
                ?: throw SwPortalException(SwPortalExceptionCode.E003)
    }
}
