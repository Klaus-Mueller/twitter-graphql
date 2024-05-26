package org.example.twitter.api;

import graphql.schema.DataFetcher;
import graphql.schema.idl.RuntimeWiring;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GraphQLWiring {

    private static List<Tweet> tweets = new ArrayList<>();
    private static AtomicInteger idCounter = new AtomicInteger();

    public static RuntimeWiring buildRuntimeWiring() {
        DataFetcher<Tweet> tweetDataFetcher = environment -> {
            String id = environment.getArgument("id");
            return tweets.stream()
                    .filter(tweet -> tweet.getId().equals(id))
                    .findFirst()
                    .orElse(null);
        };

        DataFetcher<List<Tweet>> tweetsDataFetcher = environment -> tweets;

        DataFetcher<Tweet> createTweetDataFetcher = environment -> {
            String content = environment.getArgument("content");
            String author = environment.getArgument("author");
            Tweet tweet = new Tweet(String.valueOf(idCounter.incrementAndGet()), content, author);
            tweets.add(tweet);
            return tweet;
        };

        return RuntimeWiring.newRuntimeWiring()
                .type("Query", builder -> builder
                        .dataFetcher("tweet", tweetDataFetcher)
                        .dataFetcher("tweets", tweetsDataFetcher))
                .type("Mutation", builder -> builder
                        .dataFetcher("createTweet", createTweetDataFetcher))
                .build();
    }
}

