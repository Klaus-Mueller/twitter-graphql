package org.example.twitter.api;

public class Tweet {
    public String id;
    public String content;
    public User user;

    public Tweet(String id, String content, User user) {
        this.id = id;
        this.content = content;
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
