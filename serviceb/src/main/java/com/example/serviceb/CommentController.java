package com.example.serviceb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    
    @GetMapping("{postId}")
    public List<Comment>getPostComments(@PathVariable("postId") Long postId, @RequestHeader("traceId")String traceId){
        logger.info(traceId+" - Received request from post service ");
        return CommentRepo.getByPostId(postId, traceId);
    }

}
