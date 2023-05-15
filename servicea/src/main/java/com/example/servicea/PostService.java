package com.example.servicea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private CommentClient commentClient = new CommentClient();


    public Post getPost(Long postId, String traceId){
        logger.info(traceId+" - Fetching post from DB");
        Post post = PostRepo.posts.stream().filter(p -> p.getId()==postId).findFirst().get();
        post.setComments(commentClient.getComments(postId, traceId));
        return post;
    }
}
