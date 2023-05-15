package com.example.serviceb;

public class Comment {

    private String content;
    private Long postId;

    public Comment(String content, Long postId) {
        this.content = content;
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
