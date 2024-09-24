package ua.com.dummy.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.com.dummy.app.controller.dto.CommentDTO;
import ua.com.dummy.app.facade.CommentFacade;

import java.util.List;

@Controller
public class WebController {

    private static final String ATTRIBUTE_COMMENTS = "comments";
    private static final String PAGE_HOME = "home";
    private static final String PAGE_USERS = "users";
    private final CommentFacade commentFacade;

    @Autowired
    public WebController(CommentFacade commentFacade) {
        this.commentFacade = commentFacade;
    }

    @GetMapping("/")
    public String home() {
        return PAGE_HOME;
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<CommentDTO> comments = commentFacade.getAllComments();
        model.addAttribute(ATTRIBUTE_COMMENTS, comments);
        return PAGE_USERS;
    }

}