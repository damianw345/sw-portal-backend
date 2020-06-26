package com.github.damianw345.swportalbackend.util

import org.springframework.util.SerializationUtils
import java.util.Base64
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


fun getCookie(request: HttpServletRequest, name: String): Cookie? {
    return request.cookies?.find { it.name == name }
}

fun addCookie(response: HttpServletResponse,
              name: String,
              value: String,
              maxAge: Int) {

    val cookie = Cookie(name, value)
    cookie.path = "/"
    cookie.isHttpOnly = true
    cookie.maxAge = maxAge
    response.addCookie(cookie)
}

fun deleteCookie(request: HttpServletRequest,
                 response: HttpServletResponse,
                 name: String) {

    val cookie = request.cookies?.find { it.name == name }
    cookie?.value = ""
    cookie?.path = "/"
    cookie?.maxAge = 0
    response.addCookie(cookie)
}

fun serialize(any: Any): String {
    return Base64.getUrlEncoder()
            .encodeToString(SerializationUtils.serialize(any))
}

fun <T> deserialize(cookie: Cookie, cls: Class<T>): T {
    return cls.cast(SerializationUtils.deserialize(
            Base64.getUrlDecoder().decode(cookie.value)))
}