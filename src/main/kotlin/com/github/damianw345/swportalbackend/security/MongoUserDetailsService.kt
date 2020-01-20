package com.github.damianw345.swportalbackend.security

import com.github.damianw345.swportalbackend.exception.SwPortalException
import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode.E002
import com.github.damianw345.swportalbackend.model.SecurityPrincipal
import com.github.damianw345.swportalbackend.repository.UserRepository
import mu.KotlinLogging
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class MongoUserDetailsService constructor(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        logger.info { "Searching user by username $username" }
        return userRepository
                .findByUsername(username)
                ?.let { SecurityPrincipal(it) }
                ?: throw SwPortalException(E002)
    }
}
