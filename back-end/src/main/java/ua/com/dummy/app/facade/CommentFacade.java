package ua.com.dummy.app.facade;

import ua.com.dummy.app.controller.dto.CommentDTO;

import java.util.List;

public interface CommentFacade {

    List<CommentDTO> getAllComments();

}