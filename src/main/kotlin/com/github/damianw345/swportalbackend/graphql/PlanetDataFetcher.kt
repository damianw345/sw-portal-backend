package com.github.damianw345.swportalbackend.graphql

import com.github.damianw345.swportalbackend.graphql.config.TypedDataFetcher
import com.github.damianw345.swportalbackend.model.swapi.Planet
import com.github.damianw345.swportalbackend.model.swapi.ResourceType
import com.github.damianw345.swportalbackend.repository.SwapiResourceRepository
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class PlanetDataFetcher(
    val swapiResourceRepository: SwapiResourceRepository
) : TypedDataFetcher<List<Planet>> {

    override val typeName = "Query"
    override val fieldName = ResourceType.planets.name

    override fun get(environment: DataFetchingEnvironment): List<Planet> {
        return swapiResourceRepository.findAll(ResourceType.planets)
    }
}