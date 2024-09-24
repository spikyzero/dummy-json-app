package ua.com.dummy.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.dummy.app.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}