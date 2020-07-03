package com.github.damianw345.swportalbackend.model.swapi

/**
 * Enum name represents Mongo collection and @PathVariable
 * used in SwapiResourceController.
 * @param clazz is used for reflective mappings
 * during interactions with Mongo
 */
enum class ResourceType(val clazz: Class<*>) {
    people(Character::class.java),
    films(Film::class.java),
    planets(Planet::class.java),
    species(Species::class.java),
    starships(Starship::class.java),
    vehicles(Vehicle::class.java)
}