package com.github.damianw345.swportalbackend.security

import com.github.damianw345.swportalbackend.model.security.Role.ROLE_ADMIN
import com.github.damianw345.swportalbackend.repository.UserRepository
import com.github.damianw345.swportalbackend.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository
import com.github.damianw345.swportalbackend.security.oauth.OAuth2AuthenticationFailureHandler
import com.github.damianw345.swportalbackend.security.oauth.OAuth2AuthenticationSuccessHandler
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
class WebSecurityConfig(
        private val restAuthenticationEntryPoint: RestAuthenticationEntryPoint,
        private val restAccessDeniedHandler: RestAccessDeniedHandler,
        private val jwtAuthenticationFilter: JwtAuthenticationFilter,
        private val userRepository: UserRepository,
        private val cookieAuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
        private val oAuth2UserService: OAuth2UserService<OAuth2UserRequest, OAuth2User>,
        private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
        private val oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler,
        private val passwordEncoder: PasswordEncoder
) : WebSecurityConfigurerAdapter() {

    override fun configure(authManagerBuilder: AuthenticationManagerBuilder) {
        authManagerBuilder
                .userDetailsService(userDetailsServiceBean())
                .passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests()
                .antMatchers("/admin").hasAuthority(ROLE_ADMIN.name)
                .antMatchers("/users/**", "/auth/**", "/oauth2/**").permitAll()
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
                .and()
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize")
                .authorizationRequestRepository(cookieAuthorizationRequestRepository)
                .and()
                .redirectionEndpoint()
                .baseUri("/oauth2/callback/*")
                .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler)
                .failureHandler(oAuth2AuthenticationFailureHandler)


        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/v3/api-docs/**",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",

            "/graphql",
            "/graphiql",
            "/vendor/**"
        )
    }

    override fun userDetailsServiceBean(): UserDetailsService {
        return MongoUserDetailsService(userRepository)
    }
}
