package com.gameonDatabse.gameOn.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "post_comments")
public class PostComments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = true)
    @JsonProperty("uCommentId")
    private Long uCommentId;

    @Column(name = "post_id", nullable = false)
    @JsonProperty("postId")
    private Long postId;

    @Column(name = "u_comment")
    @JsonProperty("uComment")
    private String uComment;

    // Getters
    public Long getId() {
        return id;
    }

    public Long getUCommentId() {
        return uCommentId;
    }

    public Long getPostId() {
        return postId;
    }

    public String getUComment() {
        return uComment;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUCommentId(Long uCommentId) {
        this.uCommentId = uCommentId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setUComment(String uComment) {
        this.uComment = uComment;
    }
}
