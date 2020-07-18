package com.github.damianw345.swportalbackend.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class UserDto(@field:NotBlank
                   val username: String,
                   @field:[Size(min = 8, max = 60)]
                   val password: String,
                   val roles: List<String> = listOf()) {

    constructor(username: String, roles: List<String>) : this(username, "", roles)
}
