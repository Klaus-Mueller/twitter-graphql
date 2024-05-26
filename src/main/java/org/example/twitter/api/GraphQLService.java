package org.example.twitter.api;

import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GraphQLService {
    private final String SCHEMA_FILEPATH = "src/main/resources/schema.graphqls";
    private final Logger logger = LoggerFactory.getLogger(GraphQLService.class);
    private final GraphQLWiring graphQLWiring;
    public GraphQLService() {
        this.graphQLWiring = new GraphQLWiring();
    }

    public GraphQL createGraphQL() {
        try {
            final String schemaContent = Files.readString(Path.of(SCHEMA_FILEPATH));
            // Parse the schema
            TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaContent);
            RuntimeWiring wiring = this.graphQLWiring.buildRuntimeWiring();
            GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
            // Create the GraphQL instance
            return GraphQL.newGraphQL(schema).build();
        }catch (IOException ex) {
            logger.error("Error reading schema file: {}", ex.getMessage());
        }
        return null;
    }
}

