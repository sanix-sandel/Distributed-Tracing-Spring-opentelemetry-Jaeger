package com.example.serviceb;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentRepo {

    private static final Logger logger = LoggerFactory.getLogger(CommentRepo.class);

    @Autowired
    private Tracer tracer;

    private List<Comment> commentList = Arrays.asList(
            new Comment("comment 1", 1L),
            new Comment("comment 2", 2L),
            new Comment("comment 3", 1L),
            new Comment("comment 4", 3L),
            new Comment("comment 5", 1L)
    );

    public List<Comment>getByPostId(Long postId){

        Span span = tracer.spanBuilder("Comments DB Fetch ").startSpan();

        var traceId = span.getSpanContext().getTraceId();

        logger.info(traceId+" - fetching comments from DB");

        return commentList
                .stream().filter(comment -> comment.getPostId() == postId)
                .collect(Collectors.toList());
    }

}
