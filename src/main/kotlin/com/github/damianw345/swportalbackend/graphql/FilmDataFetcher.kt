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

    override val typeName = "Query"
    override val fieldName = ResourceType.films.name

    override fun get(environment: DataFetchingEnvironment): List<Film> {
        return swapiResourceRepository.findAll(ResourceType.films)
    }
}