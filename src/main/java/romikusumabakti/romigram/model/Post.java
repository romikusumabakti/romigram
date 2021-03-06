package romikusumabakti.romigram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Post extends Base {

    @JsonIgnore
    @ManyToOne
    private Account author;

    public String getAuthorName() {
        return author.getName();
    }

    public String getAuthorUsername() {
        return author.getUsername();
    }

    @ElementCollection
    private List<String> photos;

    private String caption;

    @CreatedDate
    private Instant date;

    @JsonIgnore
    @ManyToMany
    private List<Account> likes = new ArrayList<>();

    public Boolean getLiked() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return likes.stream().anyMatch(like -> Objects.equals(like.getId(), account.getId()));
    }

    public Integer getLikeCount() {
        return likes.size();
    }

    @JsonIgnore
    @OneToMany(mappedBy = "target")
    private List<Comment> comments = new ArrayList<>();

    public Integer getCommentCount() {
        return comments.size();
    }

}
