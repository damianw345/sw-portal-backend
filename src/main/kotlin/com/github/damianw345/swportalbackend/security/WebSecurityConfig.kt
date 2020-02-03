package com.github.damianw345.swportalbackend.security

import com.github.damianw345.swportalbackend.model.Role.ADMIN
import com.github.damianw345.swportalbackend.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.access.AccessDeniedHandler


@Configuration
class WebSecurityConfig(
        private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint,
        private val userRepository: UserRepository) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun accessDeniedHandler(): AccessDeniedHandler {
        return RestAccessDeniedHandler()
    }

    override fun configure(authManagerBuilder: AuthenticationManagerBuilder) {
        authManagerBuilder
                .userDetailsService(userDetailsServiceBean())
                .passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/admin").hasRole(ADMIN.name)
                .and()
                .formLogin()
                .disable()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .httpBasic()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .realmName("SwPortal")
                .and()
                .csrf().disable()
    }

    override fun userDetailsServiceBean(): UserDetailsService {
        return MongoUserDetailsService(userRepository)
    }
}
