package com.example.servicea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private PostService postService = new PostService();

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("{postId}")
    public ResponseEntity<Post>getPost(@PathVariable("postId") Long postId){

        String traceId = UUID.randomUUID().toString();

        logger.info(traceId+" - Received external client request ");
        return ResponseEntity.ok(postService.getPost(postId, traceId));
    }
}
