package com.github.damianw345.swportalbackend.graphql

import com.github.damianw345.swportalbackend.graphql.config.TypedDataFetcher
import com.github.damianw345.swportalbackend.model.swapi.Film
import com.github.damianw345.swportalbackend.model.swapi.ResourceType
import com.github.damianw345.swportalbackend.repository.SwapiResourceRepository
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class FilmsByCharacterDataFetcher(val swapiResourceRepository: SwapiResourceRepository) :
    TypedDataFetcher<List<Film>> {

    override val typeName = "Query"
    override val fieldName = "filmsByCharacter"

    override fun get(environment: DataFetchingEnvironment): List<Film> {

        val characterId = environment.getArgument<Int>("characterId")

        return swapiResourceRepository.findAll<Film>(ResourceType.films)
            .filter { it.characters.contains(characterId) }
    }
}