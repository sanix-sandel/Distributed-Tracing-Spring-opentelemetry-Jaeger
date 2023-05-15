package com.example.servicea;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class CommentClient {

    private RestTemplate restTemplate;

    public List<Comment>getComments(Long postId){
        return Arrays.asList(restTemplate.getForObject("http://127.0.0.1:8082/"+postId, Comment[].class));
    }

}
