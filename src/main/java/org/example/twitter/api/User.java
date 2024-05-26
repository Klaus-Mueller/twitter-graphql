package org.example.twitter.api;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class User {
    private String id;
    private String username;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<User> followers;
    private Set<User> following;

    public User(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.followers = new HashSet<>();
        this.following = new HashSet<>();
    }

    // Getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }

    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    // Method to add a follower
    public void addFollower(User follower) {
        this.followers.add(follower);
    }

    // Method to remove a follower
    public void removeFollower(User follower) {
        this.followers.remove(follower);
    }

    // Method to follow another user
    public void follow(User userToFollow) {
        this.following.add(userToFollow);
        userToFollow.addFollower(this);
    }

    // Method to unfollow another user
    public void unfollow(User userToUnfollow) {
        this.following.remove(userToUnfollow);
        userToUnfollow.removeFollower(this);
    }
}
