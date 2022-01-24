package com.github.damianw345.swportalbackend.graphql

import com.github.damianw345.swportalbackend.graphql.config.TypedDataFetcher
import com.github.damianw345.swportalbackend.model.swapi.ResourceType
import com.github.damianw345.swportalbackend.model.swapi.Starship
import com.github.damianw345.swportalbackend.repository.SwapiResourceRepository
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class StarshipDataFetcher(
    val swapiResourceRepository: SwapiResourceRepository
) : TypedDataFetcher<List<Starship>> {

    override val typeName = "Query"
    override val fieldName = ResourceType.starships.name

    override fun get(environment: DataFetchingEnvironment): List<Starship> {
        return swapiResourceRepository.findAll(ResourceType.starships)
    }
}