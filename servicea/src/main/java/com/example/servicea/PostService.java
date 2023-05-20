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
    private TracerProvider tracerProvider;

    @Autowired
    private CommentClient commentClient;

    @Value("${spring.application.name}")
    private String applicationName;

    public Post getPost(Long postId, String traceId){
        logger.info(applicationName+" - "+traceId+" - Fetching post from DB");

        Tracer tracer = tracerProvider.get("Get-Post");

        Span span = tracer.spanBuilder("Fetching Post from DB").startSpan();

        try{
            Post post = PostRepo.posts.stream().filter(p -> p.getId()==postId).findFirst().get();
            span.setAttribute("post", post.toString());
            post.setComments(commentClient.getComments(postId, traceId));
            return post;
        }finally {
            span.end();
        }

    }
}
