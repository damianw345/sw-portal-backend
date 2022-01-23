package com.github.damianw345.swportalbackend.graphql

import com.github.damianw345.swportalbackend.graphql.config.TypedDataFetcher
import com.github.damianw345.swportalbackend.model.swapi.CharacterEntity
import com.github.damianw345.swportalbackend.model.swapi.ResourceType
import com.github.damianw345.swportalbackend.repository.SwapiResourceRepository
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class CharacterDataFetcher(
    val swapiResourceRepository: SwapiResourceRepository
) : TypedDataFetcher<List<CharacterEntity>> {
    override fun get(environment: DataFetchingEnvironment): List<CharacterEntity> {
        return swapiResourceRepository.findAll(ResourceType.people)
    }

    override val typeName: String
        get() = "Query"
    override val fieldName: String
        get() = "characters"
}