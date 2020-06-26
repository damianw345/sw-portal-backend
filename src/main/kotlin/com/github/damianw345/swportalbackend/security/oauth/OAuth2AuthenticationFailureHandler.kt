package com.github.damianw345.swportalbackend.security.oauth

import com.github.damianw345.swportalbackend.security.REDIRECT_URI_PARAM_COOKIE_NAME
import com.github.damianw345.swportalbackend.util.getCookie
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2AuthenticationFailureHandler(
        private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository
) : SimpleUrlAuthenticationFailureHandler() {

    override fun onAuthenticationFailure(request: HttpServletRequest,
                                         response: HttpServletResponse,
                                         exception: AuthenticationException) {

        var targetUrl = getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                ?.let { obj: Cookie -> obj.value }
                ?: "/"

        targetUrl = UriComponentsBuilder
                .fromUriString(targetUrl)
                .queryParam("error", exception.localizedMessage)
                .build().toUriString()

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response)

        redirectStrategy.sendRedirect(request, response, targetUrl)
    }
}
