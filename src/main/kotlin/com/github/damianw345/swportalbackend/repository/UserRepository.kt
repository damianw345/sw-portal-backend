package com.github.damianw345.swportalbackend.repository

import com.github.damianw345.swportalbackend.model.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : MongoRepository<User, String> {

    fun findByUsername(username: String): User?
}
