package com.github.damianw345.swportalbackend.security.oauth

import com.github.damianw345.swportalbackend.config.AppConfig
import com.github.damianw345.swportalbackend.exception.SwPortalException
import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode.E010
import com.github.damianw345.swportalbackend.security.REDIRECT_URI_PARAM_COOKIE_NAME
import com.github.damianw345.swportalbackend.util.JwtUtil
import com.github.damianw345.swportalbackend.util.getCookie
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class OAuth2AuthenticationSuccessHandler(
        private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
        private val jwtUtil: JwtUtil,
        private val appConfig: AppConfig
) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(request: HttpServletRequest,
                                         response: HttpServletResponse,
                                         authentication: Authentication) {

        val targetUrl = determineTargetUrl(request, response, authentication)

        if (response.isCommitted) {
            logger.debug("Response has already been committed. Unable to redirect to $targetUrl")
            return
        }

        clearAuthenticationAttributes(request, response)
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    protected fun clearAuthenticationAttributes(request: HttpServletRequest,
                                                response: HttpServletResponse) {
        super.clearAuthenticationAttributes(request)
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
    }

    override fun determineTargetUrl(request: HttpServletRequest,
                                    response: HttpServletResponse,
                                    authentication: Authentication): String {
        val targetUrl = getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                ?.let { cookie: Cookie -> cookie.value }
                ?.also { validateAuthorizedRedirectUri(it) }
                ?: defaultTargetUrl

        return UriComponentsBuilder
                .fromUriString(targetUrl)
                .queryParam("token", jwtUtil.buildToken(authentication))
                .build()
                .toUriString()
    }

    private fun validateAuthorizedRedirectUri(uri: String) {
        val clientRedirectUri = URI.create(uri)
        appConfig.authorizedRedirectUris
                .map { URI.create(it) }
                .firstOrNull { it.host == clientRedirectUri.host && it.port == clientRedirectUri.port }
                ?: throw SwPortalException(E010)
    }
}
