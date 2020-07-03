package com.github.damianw345.swportalbackend.repository

import com.github.damianw345.swportalbackend.model.security.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String> {

    fun findByUsername(username: String): User?
}
