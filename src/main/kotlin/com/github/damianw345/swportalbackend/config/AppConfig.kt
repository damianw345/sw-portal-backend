package com.github.damianw345.swportalbackend.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app-config")
class AppConfig {

    var token: Token = Token()
    lateinit var authorizedRedirectUris: List<String>

    class Token {
        lateinit var secret: String
        var expirationTimeMs: Int = 0
    }
}