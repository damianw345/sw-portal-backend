package com.github.damianw345.swportalbackend.model.swapi

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field

data class Film(
        @Id override val id: Int?,
        override val created: String,
        override val edited: String,
        @Field("title") override val name: String,
        override val films: List<Int> = emptyList(),
        override val species: List<Int>,
        override val vehicles: List<Int>,
        override val starships: List<Int>,
        override val characters: List<Int>,
        override val planets: List<Int>,
        val episodeId: Int,
        val openingCrawl: String,
        val director: String,
        val producer: String,
        val releaseDate: String
) : BaseSwapiResource()