package com.github.damianw345.swportalbackend.graphql

import com.github.damianw345.swportalbackend.model.swapi.CharacterModel
import com.github.damianw345.swportalbackend.model.swapi.ResourceType
import com.github.damianw345.swportalbackend.repository.SwapiResourceRepository
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class CharacterDataFetcher(val swapiResourceRepository: SwapiResourceRepository) : DataFetcher<List<CharacterModel>> {
    override fun get(environment: DataFetchingEnvironment): List<CharacterModel> {
        return swapiResourceRepository.findAll(ResourceType.people)
    }
}