package com.github.damianw345.swportalbackend.model.swapi

import org.springframework.data.mongodb.core.mapping.Document

@Document
abstract class BaseSwapiResource {
    abstract val id: Int?
    abstract val created: String
    abstract val edited: String
    abstract val name: String
    abstract val films: List<Int>
    abstract val starships: List<Int>
    abstract val vehicles: List<Int>
    abstract val species: List<Int>
    abstract val characters: List<Int>
    abstract val planets: List<Int>
}
