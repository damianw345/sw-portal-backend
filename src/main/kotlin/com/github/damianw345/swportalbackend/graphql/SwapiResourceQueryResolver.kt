package com.github.damianw345.swportalbackend.graphql

import com.github.damianw345.swportalbackend.model.swapi.*
import com.github.damianw345.swportalbackend.repository.SwapiResourceRepository
import graphql.kickstart.tools.GraphQLQueryResolver
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class SwapiResourceQueryResolver(val swapiResourceRepository: SwapiResourceRepository) : GraphQLQueryResolver {

    fun characters(environment: DataFetchingEnvironment): List<CharacterEntity> {
        return swapiResourceRepository.findAll(ResourceType.people)
    }

    fun films(environment: DataFetchingEnvironment): List<Film> {
        return swapiResourceRepository.findAll(ResourceType.films)
    }

    fun filmsByCharacter(id: Int, environment: DataFetchingEnvironment): List<Film> {
        return swapiResourceRepository.findAll<Film>(ResourceType.films)
            .filter { it.characters.contains(id) }
    }

    fun planets(environment: DataFetchingEnvironment): List<Planet> {
        return swapiResourceRepository.findAll(ResourceType.planets)
    }

    fun species(environment: DataFetchingEnvironment): List<Species> {
        return swapiResourceRepository.findAll(ResourceType.species)
    }

    fun starships(environment: DataFetchingEnvironment): List<Starship> {
        return swapiResourceRepository.findAll(ResourceType.starships)
    }

    fun vehicles(environment: DataFetchingEnvironment): List<Vehicle> {
        return swapiResourceRepository.findAll(ResourceType.vehicles)
    }

}