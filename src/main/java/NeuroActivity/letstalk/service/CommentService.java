package NeuroActivity.letstalk.service;

import NeuroActivity.letstalk.domain.Comment;
import NeuroActivity.letstalk.domain.Think;
import NeuroActivity.letstalk.domain.User;
import NeuroActivity.letstalk.domain.Views;
import NeuroActivity.letstalk.dto.EventType;
import NeuroActivity.letstalk.dto.ObjectType;
import NeuroActivity.letstalk.repository.CommentRepo;
import NeuroActivity.letstalk.util.WebSocketSender;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.function.BiConsumer;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final BiConsumer<EventType, Comment> webSocketSender;

    @Autowired
    public CommentService(CommentRepo commentRepo, WebSocketSender webSocketSender) {
        this.commentRepo = commentRepo;
        this.webSocketSender = webSocketSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    @PostMapping
    @JsonView(Views.FullComment.class)
    public Comment create(Comment comment, User user) {
        comment.setAuthor(user);

        Comment commentFromDB = commentRepo.save(comment);
        webSocketSender.accept(EventType.CREATE, commentFromDB);

        return commentFromDB;
    }
}