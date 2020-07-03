package com.github.damianw345.swportalbackend.util

import com.github.damianw345.swportalbackend.config.AppConfig
import com.github.damianw345.swportalbackend.exception.SwPortalException
import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode.E007
import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode.E008
import com.github.damianw345.swportalbackend.model.security.SecurityPrincipal
import com.github.damianw345.swportalbackend.security.AUTHORITIES_KEY
import com.github.damianw345.swportalbackend.security.TOKEN_PREFIX
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.SignatureException
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.Date

@Component
data class JwtUtil(val appConfig: AppConfig) {
    fun buildToken(username: String, authorities: List<String>): String {
        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(Date(System.currentTimeMillis() + appConfig.token.expirationTimeMs))
                .signWith(SignatureAlgorithm.HS512, appConfig.token.secret.toByteArray())
                .compact()
    }

    fun buildToken(authentication: Authentication): String {
        val securityPrincipal: SecurityPrincipal = authentication.principal as SecurityPrincipal
        return buildToken(securityPrincipal.username, securityPrincipal.authorities.map { it.authority })
    }

    fun parseToken(token: String): Claims {
        return try {
            Jwts.parser()
                    .setSigningKey(appConfig.token.secret.toByteArray())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .body
        } catch (expiredJwtException: ExpiredJwtException) {
            throw SwPortalException(E007, expiredJwtException)
        } catch (signatureException: SignatureException) {
            throw SwPortalException(E008, signatureException)
        }
    }
}
