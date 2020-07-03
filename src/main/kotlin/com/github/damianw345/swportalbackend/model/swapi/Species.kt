package com.github.damianw345.swportalbackend.model.swapi

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field

data class Species(
        @Id override val id: Int?,
        override val created: String,
        override val edited: String,
        override val name: String,
        override val films: List<Int>,
        override val species: List<Int> = emptyList(),
        override val vehicles: List<Int> = emptyList(),
        override val starships: List<Int> = emptyList(),
        @Field("people") override val characters: List<Int>,
        override val planets: List<Int> = emptyList(),
        val classification: String,
        val designation: String,
        val averageHeight: String,
        val skinColors: String,
        val hairColors: String,
        val eyeColors: String,
        val averageLifespan: String,
        @Field("homeworld") val homeWorld: Int?,
        val language: String
) : BaseSwapiResource()