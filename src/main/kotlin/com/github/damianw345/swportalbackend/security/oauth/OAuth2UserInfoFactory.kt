package com.github.damianw345.swportalbackend.security.oauth

import com.github.damianw345.swportalbackend.exception.SwPortalException
import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode
import com.github.damianw345.swportalbackend.security.AuthProvider
import com.github.damianw345.swportalbackend.security.oauth.provider.FacebookOAuth2UserInfo
import com.github.damianw345.swportalbackend.security.oauth.provider.GithubOAuth2UserInfo
import com.github.damianw345.swportalbackend.security.oauth.provider.GoogleOAuth2UserInfo
import com.github.damianw345.swportalbackend.security.oauth.provider.OAuth2UserInfo

fun getOAuth2UserInfo(registrationId: String, attributes: Map<String, String>): OAuth2UserInfo {
    return when (registrationId) {
        AuthProvider.google.name -> GoogleOAuth2UserInfo(attributes)
        AuthProvider.facebook.name -> FacebookOAuth2UserInfo(attributes)
        AuthProvider.github.name -> GithubOAuth2UserInfo(attributes)
        else -> throw SwPortalException(SwPortalExceptionCode.E009)
    }
}