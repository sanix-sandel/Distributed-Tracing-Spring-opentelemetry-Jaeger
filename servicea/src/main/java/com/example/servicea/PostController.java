package com.example.servicea;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.TracerProvider;
import io.opentelemetry.instrumentation.api.server.ServerSpan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("")
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);
    private PostService postService = new PostService();

    @Autowired
    private TracerProvider tracerProvider;

    @Value("${spring.application.name}")
    private String applicationName;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("{postId}")
    public ResponseEntity<Post>getPost(@PathVariable("postId") Long postId){

        String traceId = UUID.randomUUID().toString();

        Tracer tracer = tracerProvider.get("Get-Post");

        Span span = tracer.spanBuilder("Received external request").startSpan();
        
        try{
            logger.info(applicationName+" - "+traceId+" - Received external client request ");

            span.setAttribute("post-id", postId);
            span.setAttribute("traceId", traceId);
            span.addEvent("The service has received a new external request");
            var response = postService.getPost(postId, traceId);
            return ResponseEntity.ok(response);
        }finally {
            span.end();
        }

    }
}
