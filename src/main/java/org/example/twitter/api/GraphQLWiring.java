package org.example.twitter.api;

import graphql.schema.DataFetcher;
import graphql.schema.idl.RuntimeWiring;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GraphQLWiring {

    private List<Tweet> tweets;
    private List<User> users;
    private AtomicInteger tweetIdCounter = new AtomicInteger();
    private AtomicInteger userIdCounter = new AtomicInteger();

    public GraphQLWiring() {
        this.tweets = new ArrayList<>();
        this.users = new ArrayList<>();
        this.tweetIdCounter = new AtomicInteger();
        this.userIdCounter = new AtomicInteger();
        this.userIdCounter = new AtomicInteger();
    }

    public GraphQLWiring(List<Tweet> tweets, List<User> users, AtomicInteger tweetIdCounter, AtomicInteger userIdCounter) {
        this.tweets = tweets;
        this.users = users;
        this.tweetIdCounter = tweetIdCounter;
        this.userIdCounter = userIdCounter;
    }

    public RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder
                        .dataFetcher("tweet", tweetDataFetcher())
                        .dataFetcher("tweets", tweetsDataFetcher())
                        .dataFetcher("getUser", getUserDataFetcher()))
                .type("Mutation", builder -> builder
                        .dataFetcher("createTweet", createTweetDataFetcher())
                        .dataFetcher("createUser", createUserDataFetcher()))
                .build();
    }

    public DataFetcher<Tweet> tweetDataFetcher() {
        return environment -> {
            String id = environment.getArgument("id");
            return tweets.stream()
                    .filter(tweet -> tweet.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher<List<Tweet>> tweetsDataFetcher() {
        return environment -> tweets;
    }

    public DataFetcher<Tweet> createTweetDataFetcher() {
        return environment -> {
            String content = environment.getArgument("content");
            User user = getUser(environment.getArgument("user"));
            Tweet tweet = new Tweet(String.valueOf(tweetIdCounter.incrementAndGet()), content, user);
            tweets.add(tweet);
            return tweet;
        };
    }

    public DataFetcher<User> createUserDataFetcher() {
        return environment -> {
            String username = environment.getArgument("username");
            String email = environment.getArgument("email");
            User user = new User(String.valueOf(userIdCounter.incrementAndGet()), username, email);
            users.add(user);
            return user;
        };
    }

    public DataFetcher<User> getUserDataFetcher() {
        return environment -> {
            String id = environment.getArgument("id");
            return getUser(id);
        };
    }

    public User getUser(String id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
