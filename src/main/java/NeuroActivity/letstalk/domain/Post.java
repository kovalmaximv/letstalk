package NeuroActivity.letstalk.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @JsonView(Views.FullPost.class)
    private String link;

    @JsonView(Views.FullPost.class)
    private String linkTitle;

    @JsonView(Views.FullPost.class)
    private String linkDescription;

    @JsonView(Views.FullPost.class)
    private String linkCover;

}
