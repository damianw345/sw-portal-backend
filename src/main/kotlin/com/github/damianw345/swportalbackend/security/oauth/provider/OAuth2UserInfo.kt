package com.github.damianw345.swportalbackend.security.oauth.provider

abstract class OAuth2UserInfo(var attributes: Map<String, String>) {
    abstract val id: String // user id in external system
    abstract val name: String // user real name
    abstract val email: String // user email
}
