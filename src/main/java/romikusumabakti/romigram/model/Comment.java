package romikusumabakti.romigram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
public class Comment extends Base {

    @JsonIgnore
    @ManyToOne
    private Post target;

    @JsonIgnore
    @ManyToOne
    private Account author;

    public String getAuthorName() {
        return author.getName();
    }

    public String getAuthorUsername() {
        return author.getUsername();
    }

    private String content;

    @CreatedDate
    private Instant date;

    @JsonIgnore
    @ManyToMany
    private List<Account> likes;

    public Integer getLikeCount() {
        return likes.size();
    }

}
