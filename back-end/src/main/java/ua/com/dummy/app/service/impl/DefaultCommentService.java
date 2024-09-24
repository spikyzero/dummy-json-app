package ua.com.dummy.app.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ua.com.dummy.app.model.Comment;
import ua.com.dummy.app.repository.CommentRepository;
import ua.com.dummy.app.service.CommentService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DefaultCommentService implements CommentService {

    private static final String SUCCESS_MESSAGE = "Comments processed and saved successfully";
    private static final String ERROR_MESSAGE = "Error while processing comments";
    private static final String DUMMY_JSON_SITE = "https://dummyjson.com";
    private static final String DATE_PATTERN = "dd-MM-yyyy HH:mm:ss";
    private static final String ROOT_NODE = "comments";
    private static final String FIELD_USERNAME = "username";
    private static final String FIELD_POST_ID = "postId";
    private static final String FIELD_BODY = "body";
    private static final String FIELD_USER = "user";
    private final CommentRepository commentRepository;
    private final WebClient webClient;

    @Autowired
    public DefaultCommentService(WebClient.Builder webClientBuilder, CommentRepository commentRepository) {
        this.webClient = webClientBuilder.baseUrl(DUMMY_JSON_SITE).build();
        this.commentRepository = commentRepository;
    }

    @PostConstruct
    public void fetchAndSaveComments() {
        if (commentRepository.count() == 0) {
            commentRepository.deleteAll();
            initComments().subscribe();
        }
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    private Mono<String> initComments() {
        return this.webClient
                .get()
                .uri("/comments")
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(this::processComments);
    }

    private Mono<String> processComments(String json) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode commentsNode = rootNode.path(ROOT_NODE);
            List<Comment> comments = new ArrayList<>();
            commentsNode.forEach(commentNode -> {
                Comment comment = convertToComment(commentNode);
                comments.add(comment);
            });
            commentRepository.saveAll(comments);
            return Mono.just(SUCCESS_MESSAGE);
        } catch (Exception e) {
            log.error(ERROR_MESSAGE, e);
            return Mono.just(ERROR_MESSAGE);
        }
    }

    private Comment convertToComment(JsonNode commentNode) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        String updatedAt = LocalDateTime.now().format(formatter);
        Comment comment = new Comment();
        comment.setBody(commentNode.path(FIELD_BODY).asText());
        comment.setPostId(commentNode.path(FIELD_POST_ID).asLong());
        comment.setUsername(capitalizeUsername(commentNode.path(FIELD_USER).path(FIELD_USERNAME).asText()));
        comment.setUpdatedAt(updatedAt);
        return comment;
    }

    private String capitalizeUsername(String username) {
        if (username == null || username.isEmpty()) {
            return username;
        }
        return username.substring(0, 1).toUpperCase() + username.substring(1);
    }

}