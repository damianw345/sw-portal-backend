package com.github.damianw345.swportalbackend.model.swapi

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field

data class Character(
        @Id override val id: Int?,
        override val created: String,
        override val edited: String,
        override val name: String,
        override val films: List<Int>,
        override val species: List<Int>,
        override val vehicles: List<Int>,
        override val starships: List<Int>,
        override val characters: List<Int> = emptyList(),
        override val planets: List<Int> = emptyList(),
        val height: String,
        val mass: String,
        val hairColor: String,
        val skinColor: String,
        val eyeColor: String,
        val birthYear: String,
        val gender: String,
        @Field("homeworld") val homeWorld: Int
) : BaseSwapiResource()