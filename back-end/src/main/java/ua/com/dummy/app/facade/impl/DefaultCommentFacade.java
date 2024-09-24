package ua.com.dummy.app.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.com.dummy.app.controller.dto.CommentDTO;
import ua.com.dummy.app.converter.CommentMapper;
import ua.com.dummy.app.facade.CommentFacade;
import ua.com.dummy.app.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultCommentFacade implements CommentFacade {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Autowired
    public DefaultCommentFacade(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @Override
    public List<CommentDTO> getAllComments() {
        return commentService.getAllComments()
                .stream()
                .map(commentMapper::toCommentDTO)
                .collect(Collectors.toList());
    }

}