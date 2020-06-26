package com.github.damianw345.swportalbackend.model

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class SecurityPrincipal constructor(private val user: User) : UserDetails, OAuth2User {

    override fun getAuthorities(): List<SimpleGrantedAuthority> {
        return user.roles.map { SimpleGrantedAuthority(it) }
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return user.username
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun getAttributes(): Map<String, String> {
        return user.attributes
    }

    override fun getName(): String? {
        return user.username
    }
}
