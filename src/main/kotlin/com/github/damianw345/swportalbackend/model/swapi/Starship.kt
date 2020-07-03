package com.github.damianw345.swportalbackend.model.swapi

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field

data class Starship(
        @Id override val id: Int?,
        override val created: String,
        override val edited: String,
        override val name: String,
        override val films: List<Int>,
        override val species: List<Int> = emptyList(),
        override val vehicles: List<Int> = emptyList(),
        override val starships: List<Int> = emptyList(),
        @Field("pilots") override val characters: List<Int>,
        override val planets: List<Int> = emptyList(),
        val model: String,
        val manufacturer: String,
        val costInCredits: String,
        val length: String,
        val maxAtmospheringSpeed: String,
        val crew: String,
        val passengers: String,
        val cargoCapacity: String,
        val consumables: String,
        val hyperdriveRating: String,
        val starshipClass: String
) : BaseSwapiResource()