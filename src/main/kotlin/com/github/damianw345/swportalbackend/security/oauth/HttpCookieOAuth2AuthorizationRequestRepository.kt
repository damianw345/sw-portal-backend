package com.github.damianw345.swportalbackend.security.oauth

import com.github.damianw345.swportalbackend.security.COOKIE_EXPIRE_SECONDS
import com.github.damianw345.swportalbackend.security.OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME
import com.github.damianw345.swportalbackend.security.REDIRECT_URI_PARAM_COOKIE_NAME
import com.github.damianw345.swportalbackend.util.addCookie
import com.github.damianw345.swportalbackend.util.deleteCookie
import com.github.damianw345.swportalbackend.util.deserialize
import com.github.damianw345.swportalbackend.util.getCookie
import com.github.damianw345.swportalbackend.util.serialize
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class HttpCookieOAuth2AuthorizationRequestRepository : AuthorizationRequestRepository<OAuth2AuthorizationRequest?> {

    override fun loadAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        return getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                ?.let { cookie -> deserialize(cookie, OAuth2AuthorizationRequest::class.java) }
    }

    override fun saveAuthorizationRequest(authorizationRequest: OAuth2AuthorizationRequest?, request: HttpServletRequest, response: HttpServletResponse) {
        if (authorizationRequest == null) {
            deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
            deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME)
            return
        }

        addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME, serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS)
        val redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME)
        if (redirectUriAfterLogin.isNotBlank()) {
            addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, COOKIE_EXPIRE_SECONDS)
        }
    }

    override fun removeAuthorizationRequest(request: HttpServletRequest): OAuth2AuthorizationRequest? {
        return loadAuthorizationRequest(request)
    }

    fun removeAuthorizationRequestCookies(request: HttpServletRequest, response: HttpServletResponse) {
        deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
        deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME)
    }
}
