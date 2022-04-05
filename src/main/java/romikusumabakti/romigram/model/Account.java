package romikusumabakti.romigram.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
public class Account extends Base implements UserDetails {

    private String name;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    private List<Post> posts;

    @JsonIgnore
    @ManyToMany
    private List<Account> followers;

    @JsonIgnore
    @ManyToMany(mappedBy = "followers")
    private List<Account> followed;

    @JsonIgnore
    @ManyToMany(mappedBy = "likes")
    private List<Post> likedPosts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
