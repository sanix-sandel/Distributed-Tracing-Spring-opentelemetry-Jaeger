package com.example.servicea;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.TracerProvider;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.TextMapPropagator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private TracerProvider tracerProvider;

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.application.name}")
    private String applicationName;

    public List<Comment>getComments(Long postId, String traceId){

        HttpHeaders headers = new HttpHeaders();
        Context context = Context.current();

        headers.add("traceId", traceId);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        logger.info(applicationName+" - "+traceId+" - Fetching comments from comment service");

        Tracer tracer = tracerProvider.get("Get-Post");

        Span span = tracer.spanBuilder("Fetching comments from comment-service").startSpan();


        try{
            ResponseEntity<Comment[]> response = restTemplate.exchange("http://127.0.0.1:8081/"+postId, HttpMethod.GET, entity, Comment[].class);
            span.setAttribute("comments", response.getBody().toString());

            return Arrays.asList(response.getBody());
        }
        catch(Exception e){
            span.recordException(e);
            span.setStatus(StatusCode.ERROR);
            return Arrays.asList();
        }
        finally {
            span.end();
        }

    }

}
