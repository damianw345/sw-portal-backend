package com.github.damianw345.swportalbackend.service

interface ResourceService {
    fun getResourceByTypeAndId(type: String, id: Int): String?
}
