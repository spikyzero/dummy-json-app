package ua.com.dummy.app.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ua.com.dummy.app.controller.dto.CommentDTO;
import ua.com.dummy.app.model.Comment;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "body", target = "body")
    @Mapping(source = "postId", target = "postId")
    @Mapping(source = "updatedAt", target = "updatedAt")
    @Mapping(source = "username", target = "username")
    CommentDTO toCommentDTO(Comment comment);

}