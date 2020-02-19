package com.github.damianw345.swportalbackend.util

import com.github.damianw345.swportalbackend.exception.SwPortalException
import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode.E007
import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode.E008
import com.github.damianw345.swportalbackend.security.AUTHORITIES_KEY
import com.github.damianw345.swportalbackend.security.TOKEN_PREFIX
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.SignatureException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.Date

@Component
data class JwtUtil(
        @Value("\${app-config.token.expirationTimeMs}") private val expirationTimeMs: Long,
        @Value("\${app-config.token.secret}") private val tokenSecret: String
) {
    fun buildToken(username: String, authorities: List<String>): String {
        return Jwts.builder()
                .setSubject(username)
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(SignatureAlgorithm.HS512, tokenSecret.toByteArray())
                .compact()
    }

    fun parseToken(token: String): Claims {
        return try {
            Jwts.parser()
                    .setSigningKey(tokenSecret.toByteArray())
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .body
        } catch (expiredJwtException: ExpiredJwtException) {
            throw SwPortalException(E007, expiredJwtException)
        } catch (signatureException: SignatureException) {
            throw SwPortalException(E008, signatureException)
        }
    }
}
