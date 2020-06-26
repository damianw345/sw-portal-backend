package com.github.damianw345.swportalbackend.security.oauth

import com.github.damianw345.swportalbackend.exception.SwPortalException
import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode
import com.github.damianw345.swportalbackend.exception.SwPortalExceptionCode.E002
import com.github.damianw345.swportalbackend.model.Role
import com.github.damianw345.swportalbackend.model.SecurityPrincipal
import com.github.damianw345.swportalbackend.model.User
import com.github.damianw345.swportalbackend.repository.UserRepository
import com.github.damianw345.swportalbackend.security.AuthProvider
import com.github.damianw345.swportalbackend.security.oauth.provider.OAuth2UserInfo
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class OAuth2UserService(private val userRepository: UserRepository,
                        private val passwordEncoder: PasswordEncoder
) : DefaultOAuth2UserService() {

    override fun loadUser(oAuth2UserRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(oAuth2UserRequest)
        return try {
            processOAuth2User(oAuth2UserRequest, oAuth2User)
        } catch (ex: Exception) {
            throw SwPortalException(SwPortalExceptionCode.E000, ex.cause)
        }
    }

    private fun processOAuth2User(oAuth2UserRequest: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2User {

        val attributes = oAuth2User
                .attributes
                .filterValues { it != null }
                .mapValues { it.value.toString() }

        val oAuth2UserInfo: OAuth2UserInfo = getOAuth2UserInfo(oAuth2UserRequest.clientRegistration.registrationId, attributes)
        if (oAuth2UserInfo.email.isNullOrBlank()) {
            throw SwPortalException(E002)
        }

        return SecurityPrincipal(getUser(oAuth2UserRequest, oAuth2UserInfo))
    }

    private fun getUser(oAuth2UserRequest: OAuth2UserRequest, oAuth2UserInfo: OAuth2UserInfo): User {
        return oAuth2UserInfo.email
                .let { userRepository.findByUsername(it) }
                ?.let { updateExistingUser(it, oAuth2UserRequest, oAuth2UserInfo) }
                ?: registerNewUser(oAuth2UserRequest, oAuth2UserInfo)
    }

    private fun registerNewUser(oAuth2UserRequest: OAuth2UserRequest, oAuth2UserInfo: OAuth2UserInfo): User {
        val user = createUser(oAuth2UserInfo, oAuth2UserRequest)
        return userRepository.save(user)
    }

    private fun createUser(oAuth2UserInfo: OAuth2UserInfo, oAuth2UserRequest: OAuth2UserRequest): User {
        val provider = oAuth2UserRequest.clientRegistration.registrationId
        return User(
                username = oAuth2UserInfo.email,
                password = passwordEncoder.encode("${provider}__${UUID.randomUUID()}"),
                roles = listOf(Role.ROLE_USER.name),
                authProvider = AuthProvider.valueOf(provider),
                attributes = oAuth2UserInfo.attributes
        )
    }

    private fun updateExistingUser(existingUser: User,
                                   oAuth2UserRequest: OAuth2UserRequest,
                                   oAuth2UserInfo: OAuth2UserInfo): User {
        userRepository.delete(existingUser)
        return userRepository.save(createUser(oAuth2UserInfo, oAuth2UserRequest))
    }
}
