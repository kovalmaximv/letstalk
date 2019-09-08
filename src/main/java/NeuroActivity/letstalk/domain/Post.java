package NeuroActivity.letstalk.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@MappedSuperclass
@ToString(of = {"id", "text"})
@EqualsAndHashCode(of = {"id"})
@Data
public abstract class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.id.class)
    protected Long id;
    @JsonView(Views.IdText.class)
    protected String text;
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyy-mm-dd HH:mm")
    @JsonView(Views.FullPost.class)
    protected LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonView(Views.FullPost.class)
    private User author;

    @OneToMany(mappedBy = "think", orphanRemoval = true)
    @JsonView(Views.FullPost.class)
    private List<Comment> comments;

    @JsonView(Views.FullPost.class)
    private String link;

    @JsonView(Views.FullPost.class)
    private String linkTitle;

    @JsonView(Views.FullPost.class)
    private String linkDescription;

    @JsonView(Views.FullPost.class)
    private String linkCover;

}