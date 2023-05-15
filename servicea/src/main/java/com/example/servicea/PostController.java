package com.example.servicea;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class PostController {

    @GetMapping("postId")
    public ResponseEntity<Post>getPost(@PathVariable("postId") Long postId){
        return ResponseEntity.ok(PostRepo.getPost(postId));
    }
}