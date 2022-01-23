package com.github.damianw345.swportalbackend.graphql

import com.github.damianw345.swportalbackend.graphql.config.TypedDataFetcher
import com.github.damianw345.swportalbackend.model.swapi.ResourceType
import com.github.damianw345.swportalbackend.model.swapi.Vehicle
import com.github.damianw345.swportalbackend.repository.SwapiResourceRepository
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class VehicleDataFetcher(
    val swapiResourceRepository: SwapiResourceRepository
) : TypedDataFetcher<List<Vehicle>> {
    override fun get(environment: DataFetchingEnvironment): List<Vehicle> {
        return swapiResourceRepository.findAll(ResourceType.vehicles)
    }

    override val typeName: String
        get() = "Query"
    override val fieldName: String
        get() = ResourceType.vehicles.name
}