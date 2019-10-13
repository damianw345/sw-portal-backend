package com.github.damianw345.swportalbackend.model

import org.bson.Document
import org.springframework.data.annotation.Id

data class Resource(@Id val id: Int, val body: Document)
