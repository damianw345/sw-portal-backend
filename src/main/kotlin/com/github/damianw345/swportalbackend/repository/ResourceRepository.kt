package com.github.damianw345.swportalbackend.repository

interface ResourceRepository {
    fun getResourceByTypeAndId(type: String, id: Int): String?
}
