package com.example.servicea;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class CommentClient {

    private static final Logger logger = LoggerFactory.getLogger(CommentClient.class);

    private RestTemplate restTemplate = new RestTemplate();

    public List<Comment>getComments(Long postId, String traceId){

        HttpHeaders headers = new HttpHeaders();
        headers.add("traceId", traceId);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        logger.info(traceId+" - Fetching comments from comment service");
        ResponseEntity<Comment[]> response = restTemplate.exchange("http://127.0.0.1:8081/"+postId, HttpMethod.GET, entity, Comment[].class);

        return Arrays.asList(response.getBody());
    }

}
