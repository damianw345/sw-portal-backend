package com.github.damianw345.swportalbackend.model.swapi

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field

data class Planet(
        @Id override val id: Int?,
        override val created: String,
        override val edited: String,
        override val name: String,
        override val films: List<Int>,
        override val species: List<Int> = emptyList(),
        override val vehicles: List<Int> = emptyList(),
        override val starships: List<Int> = emptyList(),
        @Field("residents") override val characters: List<Int>,
        override val planets: List<Int> = emptyList(),
        val rotationPeriod: String,
        val orbitalPeriod: String,
        val diameter: String,
        val climate: String,
        val gravity: String,
        val terrain: String,
        val surfaceWater: String,
        val population: String
) : BaseSwapiResource()