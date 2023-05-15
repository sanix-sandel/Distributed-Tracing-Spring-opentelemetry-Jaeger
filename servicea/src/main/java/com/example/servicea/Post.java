package com.example.servicea;

import java.util.ArrayList;
import java.util.List;

public class Post {
    public Post(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }


    private Long id;
    private String title;
    private String content;

    private List<Comment> comments = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
