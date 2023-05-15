package com.example.servicea;

import org.springframework.stereotype.Service;

@Service
public class PostService {

    private CommentClient commentClient = new CommentClient();


    public Post getPost(Long postId){

        Post post = PostRepo.posts.stream().filter(p -> p.getId()==postId).findFirst().get();
        post.setComments(commentClient.getComments(postId));
        return post;
    }
}
