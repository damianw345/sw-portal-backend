package com.github.damianw345.swportalbackend.graphql

import com.github.damianw345.swportalbackend.graphql.config.TypedDataFetcher
import com.github.damianw345.swportalbackend.model.swapi.Film
import com.github.damianw345.swportalbackend.model.swapi.ResourceType
import com.github.damianw345.swportalbackend.repository.SwapiResourceRepository
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class FilmDataFetcher(
    val swapiResourceRepository: SwapiResourceRepository
) : TypedDataFetcher<List<Film>> {
    override fun get(environment: DataFetchingEnvironment): List<Film> {
        return swapiResourceRepository.findAll(ResourceType.films)
    }

    override val typeName: String
        get() = "Query"
    override val fieldName: String
        get() = ResourceType.films.name
}