package com.github.damianw345.swportalbackend.security

import com.github.damianw345.swportalbackend.util.JwtUtil
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationFilter(private val jwtUtil: JwtUtil) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  filterChain: FilterChain) {

        val token = request.getHeader(HEADER_STRING)
                ?.takeIf { it.startsWith(TOKEN_PREFIX) }
                ?: run {
                    filterChain.doFilter(request, response)
                    return
                }

        SecurityContextHolder.getContext().authentication = getAuthentication(token)
        filterChain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        return UsernamePasswordAuthenticationToken(
                getUsername(token),
                null,
                getAuthorities(token)
        )
    }

    private fun getUsername(token: String): String {
        return jwtUtil.parseToken(token).subject
    }

    private fun getAuthorities(token: String): List<GrantedAuthority> {
        return getGrantedAuthorities(
                jwtUtil.parseToken(token).get(AUTHORITIES_KEY, List::class.java) as List<String>
        )
    }

    private fun getGrantedAuthorities(roles: List<String>): List<GrantedAuthority> {
        return roles.map { SimpleGrantedAuthority(it) }
    }
}
