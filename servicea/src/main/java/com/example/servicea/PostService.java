package com.example.servicea;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.TracerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    @Autowired
    private Tracer tracer;

    @Autowired
    private CommentClient commentClient;

    @Autowired
    private PostRepo postRepo;

    @Value("${spring.application.name}")
    private String applicationName;

    public Post getPost(Long postId){

        Span span = tracer.spanBuilder("Business logic").startSpan();
        var traceId = span.getSpanContext().getTraceId();

        try{

            logger.info(applicationName+" - "+traceId+" - Fetching post from DB");

            Post post = postRepo.getPost(postId);
            span.setAttribute("post", post.toString());
            post.setComments(commentClient.getComments(postId, traceId));
            return post;
        }finally {
            span.end();
        }

    }
}
