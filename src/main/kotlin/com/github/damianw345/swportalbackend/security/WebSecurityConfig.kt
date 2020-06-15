package com.github.damianw345.swportalbackend.security

import com.github.damianw345.swportalbackend.model.Role.ROLE_ADMIN
import com.github.damianw345.swportalbackend.repository.UserRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
class WebSecurityConfig(
        private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint,
        private val restAccessDeniedHandler: RestAccessDeniedHandler,
        private val jwtAuthenticationFilter: JwtAuthenticationFilter,
        private val userRepository: UserRepository) : WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(authManagerBuilder: AuthenticationManagerBuilder) {
        authManagerBuilder
                .userDetailsService(userDetailsServiceBean())
                .passwordEncoder(passwordEncoder())
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers("/admin").hasAuthority(ROLE_ADMIN.name)
                .antMatchers("/users/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .disable()
                .exceptionHandling()
                .accessDeniedHandler(restAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .and()
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
        )
    }

    override fun userDetailsServiceBean(): UserDetailsService {
        return MongoUserDetailsService(userRepository)
    }
}
