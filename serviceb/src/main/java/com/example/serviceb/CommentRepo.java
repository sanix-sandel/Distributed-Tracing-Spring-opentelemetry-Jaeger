package com.example.serviceb;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommentRepo {

    private static List<Comment> commentList = Arrays.asList(
            new Comment("comment 1", 1L),
            new Comment("comment 2", 2L),
            new Comment("comment 3", 1L),
            new Comment("comment 4", 3L),
            new Comment("comment 5", 1L)
    );

    public static List<Comment>getByPostId(Long postId){
        return commentList
                .stream().filter(comment -> comment.getPostId() == postId)
                .collect(Collectors.toList());
    }

}
