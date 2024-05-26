package org.example.twitter.api.test;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.example.twitter.api.GraphQLService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class GraphQLServiceTest {

    private GraphQLService graphQLService;

    @BeforeEach
    public void setUp() {
        this.graphQLService = new GraphQLService();
    }

    @Test
    public void testCreateGraphQL() {
        // Mock GraphQL execution result
        ExecutionResult executionResult = Mockito.mock(ExecutionResult.class);
        when(executionResult.getData()).thenReturn("mockData");

        // Mock GraphQL instance
        GraphQL mockGraphQL = Mockito.mock(GraphQL.class);
        when(mockGraphQL.execute(any(ExecutionInput.class))).thenReturn(executionResult);

        // Invoke the method under test
        GraphQL actualGraphQL = graphQLService.createGraphQL();

        // Assert that the returned GraphQL object is not null
        assertNotNull(actualGraphQL);
    }
}
