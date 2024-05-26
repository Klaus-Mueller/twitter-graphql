package org.example.twitter.api;

import static spark.Spark.*;

import com.google.gson.Gson;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        // Load the schema file
        Path schemaFilePath = Path.of("src/main/resources/schema.graphqls");
        String schemaContent = Files.readString(schemaFilePath);

        // Parse the schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaContent);
        RuntimeWiring wiring = GraphQLWiring.buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);

        // Create the GraphQL instance
        GraphQL graphQL = GraphQL.newGraphQL(schema).build();

        // Set up Spark and expose the API
        port(8080);
        post("/graphql", (req, res) -> {
            ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                    .query(req.body())
                    .build();

            ExecutionResult executionResult = graphQL.execute(executionInput);

            Map<String, Object> result = executionResult.toSpecification();
            res.type("application/json");
            return new Gson().toJson(result);
        });
    }
}
