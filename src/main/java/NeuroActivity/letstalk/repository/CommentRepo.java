package NeuroActivity.letstalk.repository;

import NeuroActivity.letstalk.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment, Long> {

}