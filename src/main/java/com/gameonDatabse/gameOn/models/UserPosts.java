package com.gameonDatabse.gameOn.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class UserPosts {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long authorId;
    private String mediaContent;
    private String postTitle;
    private String postDescription;
    private Date postDate;

    // Getters
    public long getId() {
        return id;
    }

    public long getAuthorId() {
        return authorId;
    }

    public String getMediaContent() {
        return mediaContent;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public Date getPostDate() {
        return postDate;
    }

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public void setMediaContent(String mediaContent) {
        this.mediaContent = mediaContent;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
}
