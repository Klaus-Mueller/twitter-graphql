package org.example.twitter.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.port;
import static spark.Spark.post;

public class Api {
    private static final Logger logger = LoggerFactory.getLogger(Api.class);
    private final int PORT_NUMBER = 8080;
    GraphQLService graphQLService;

    public Api() {
        this.graphQLService = new GraphQLService();
    }

    public void expose() {
        try {
            GraphQL graphQL = graphQLService.createGraphQL();
            // Create a Gson instance
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            // Set up Spark and expose the API
            port(this.PORT_NUMBER);
            post("/graphql", (req, res) -> {
                ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                        .query(req.body())
                        .build();

                ExecutionResult executionResult = graphQL.execute(executionInput);

                res.type("application/json");
                return gson.toJson(executionResult.toSpecification());
            });
            logger.info("API successfully exposed on port {}", this.PORT_NUMBER);
        } catch (Exception e) {
            logger.error("Error exposing API: {}", e.getMessage());
        }
    }
}
