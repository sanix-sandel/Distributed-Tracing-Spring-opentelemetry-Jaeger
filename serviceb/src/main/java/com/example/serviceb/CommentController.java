package com.example.serviceb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
public class CommentController {

    @GetMapping("{postId}")
    public List<Comment>getPostComments(@PathVariable("postId") Long postId){
        return CommentRepo.getByPostId(postId);
    }

}
