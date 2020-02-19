package com.github.damianw345.swportalbackend.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class UserDto(@field:NotBlank
                   val username: String,
                   @field:[Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d!\$%@#£€*?&]{8,}\$")]
                   val password: String,
                   val roles: List<String> = listOf()) {

    constructor(username: String, roles: List<String>) : this(username, "", roles)
}
