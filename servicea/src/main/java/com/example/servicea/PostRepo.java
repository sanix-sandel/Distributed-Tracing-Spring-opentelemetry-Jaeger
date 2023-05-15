package com.example.servicea;

import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostRepo {

    private static CommentClient commentClient;

    private static List<Post>posts = Arrays.asList(
            new Post(1L, "post 1", ""),
            new Post(2L, "post 2", ""),
            new Post(3L, "post 3", ""),
            new Post(4L, "post 4", ""),
            new Post(5L, "post 5", "")
    );


    public static Post getPost(Long postId){

        Post post = posts.stream().filter(p -> p.getId()==postId).findFirst().get();
        post.setComments(commentClient.getComments(postId));
        return post;
    }

}
