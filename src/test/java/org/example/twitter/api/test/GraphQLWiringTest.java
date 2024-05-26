package org.example.twitter.api.test;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.example.twitter.api.GraphQLWiring;
import org.example.twitter.api.Tweet;
import org.example.twitter.api.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GraphQLWiringTest {

    private GraphQLWiring graphQLWiring;

    @BeforeEach
    public void setUp() {
        List<Tweet> tweets = new ArrayList<>();
        List<User> users = new ArrayList<>();
        AtomicInteger tweetIdCounter = new AtomicInteger();
        AtomicInteger userIdCounter = new AtomicInteger();

        // Create some mock users
        User user1 = new User("1", "user1", "user1@example.com");
        User user2 = new User("2", "user2", "user2@example.com");
        users.add(user1);
        users.add(user2);

        // Create some mock tweets
        Tweet tweet1 = new Tweet("1", "Hello, world!", user1);
        Tweet tweet2 = new Tweet("2", "Another tweet", user2);
        tweets.add(tweet1);
        tweets.add(tweet2);

        graphQLWiring = new GraphQLWiring(tweets, users, tweetIdCounter, userIdCounter);
    }

    @Test
    public void testCreateUserDataFetcher() throws Exception {
        DataFetcher<User> createUserDataFetcher = graphQLWiring.createUserDataFetcher();
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        when(environment.getArgument("username")).thenReturn("newUser");
        when(environment.getArgument("email")).thenReturn("newuser@example.com");

        User newUser = createUserDataFetcher.get(environment);
        assertEquals("newUser", newUser.getUsername());
        assertEquals("newuser@example.com", newUser.getEmail());
    }

    @Test
    public void testCreateTweetDataFetcher() throws Exception {
        DataFetcher<Tweet> createTweetDataFetcher = graphQLWiring.createTweetDataFetcher();
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        when(environment.getArgument("content")).thenReturn("This is a new tweet");
        when(environment.getArgument("user")).thenReturn("1");

        Tweet newTweet = createTweetDataFetcher.get(environment);
        assertEquals("This is a new tweet", newTweet.getContent());
        assertEquals("1", newTweet.getUser().getId());
    }

    @Test
    public void testGetUserDataFetcher() throws Exception {
        DataFetcher<User> getUserDataFetcher = graphQLWiring.getUserDataFetcher();
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        when(environment.getArgument("id")).thenReturn("1");

        User user = getUserDataFetcher.get(environment);
        assertEquals("user1", user.getUsername());
        assertEquals("user1@example.com", user.getEmail());
    }

    @Test
    public void testTweetDataFetcher() throws Exception {
        DataFetcher<Tweet> tweetDataFetcher = graphQLWiring.tweetDataFetcher();
        DataFetchingEnvironment environment = mock(DataFetchingEnvironment.class);

        when(environment.getArgument("id")).thenReturn("1");

        Tweet tweet = tweetDataFetcher.get(environment);
        assertEquals("Hello, world!", tweet.getContent());
    }
}
