package com.svayzda.entities;


public class Post {

    private int postId;
    private String title;
    private String text;
    private int visibility;
    private boolean disabledComments;
    private String authorUsername;

    public Post(int postId, String title, String text, int visibility, boolean disabledComments, String authorUsername) {
        this.postId = postId;
        this.title = title;
        this.text = text;
        this.visibility = visibility;
        this.disabledComments = disabledComments;
        this.authorUsername = authorUsername;
    }

    public Post(String title, String text, int visibility, boolean disabledComments, String authorUsername) {
        this.title = title;
        this.text = text;
        this.visibility = visibility;
        this.disabledComments = disabledComments;
        this.authorUsername = authorUsername;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public boolean isDisabledComments() {
        return disabledComments;
    }

    public void setDisabledComments(boolean disabledComments) {
        this.disabledComments = disabledComments;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorUsername() {
        return authorUsername;
    }

    public void setAuthorUsername(String authorUsername) {
        this.authorUsername = authorUsername;
    }

    @Override
    public String toString() {
        return "Post{" +
                " title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", authorUsername='" + authorUsername + '\'' +
                '}';
    }
}