package com.github.damianw345.swportalbackend.graphql

import graphql.GraphQL
import graphql.schema.GraphQLSchema
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeDefinitionRegistry
import graphql.schema.idl.TypeRuntimeWiring.newTypeWiring
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileNotFoundException

const val GRAPHQL_SCHEMA_FILE_NAME = "/schema.graphql"

@Configuration
class GraphQLConfiguration(
    private val characterDataFetcher: CharacterDataFetcher,
) {

    @Bean
    fun graphQL(): GraphQL {
        val typeRegistry: TypeDefinitionRegistry = SchemaParser().parse(getSchema())
        val runtimeWiring: RuntimeWiring = buildWiring()
        val graphQLSchema: GraphQLSchema = SchemaGenerator().makeExecutableSchema(typeRegistry, runtimeWiring)
        return GraphQL.newGraphQL(graphQLSchema).build()
    }

    private fun getSchema() =
        javaClass.getResource(GRAPHQL_SCHEMA_FILE_NAME)?.readText()
            ?: throw FileNotFoundException("File $GRAPHQL_SCHEMA_FILE_NAME not found")

    private fun buildWiring(): RuntimeWiring {
        return RuntimeWiring.newRuntimeWiring()
            .type(newTypeWiring("Query").dataFetcher("characters", characterDataFetcher))
            .build()
    }
}