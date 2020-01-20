package com.github.damianw345.swportalbackend.util

import com.fasterxml.jackson.databind.ObjectMapper

fun convertObjectToJson(any: Any): String = ObjectMapper().writeValueAsString(any)
