package com.github.damianw345.swportalbackend.security.oauth.provider

class GoogleOAuth2UserInfo(attributes: Map<String, String>) : OAuth2UserInfo(attributes) {
    override val id: String
        get() = attributes["sub"] ?: ""

    override val name: String
        get() = attributes["name"] ?: ""

    override val email: String
        get() = attributes["email"] ?: ""
}
