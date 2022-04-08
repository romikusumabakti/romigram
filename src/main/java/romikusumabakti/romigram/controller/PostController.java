package romikusumabakti.romigram.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import romikusumabakti.romigram.model.Account;
import romikusumabakti.romigram.model.Comment;
import romikusumabakti.romigram.model.Post;
import romikusumabakti.romigram.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostRepository repository;

    public PostController(PostRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    List<Post> getAll(@AuthenticationPrincipal Account account) {
        File[] files = new File("src/main/resources/static/photos/temp/").listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isFile() && file.getName().startsWith(account.getId() + "-")) {
                file.delete();
            }
        }
        return repository.findAllByOrderByDateDesc();
    }

    @GetMapping("/{id}")
    Post getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @GetMapping("/{id}/likes")
    List<Account> getLikes(@PathVariable Long id) {
        return getById(id).getLikes();
    }

    @GetMapping("/{id}/comments")
    List<Comment> getComments(@PathVariable Long id) {
        return getById(id).getComments();
    }

    @PostMapping("/{id}/like")
    Post like(@PathVariable Long id, @AuthenticationPrincipal Account account) {
        Post post = getById(id);
        if (!post.getLiked()) {
            post.getLikes().add(account);
        } else {
            post.getLikes().removeIf(a -> Objects.equals(a.getId(), account.getId()));
        }
        return repository.save(post);
    }

    @PostMapping()
    Post create(@RequestBody Post newPost, @AuthenticationPrincipal Account account) throws IOException {
        newPost.setAuthor(account);
        newPost.setDate(Instant.now());
        Path sourcePath = Paths.get("src/main/resources/static/photos/temp/" + newPost.getPhotos().get(0) + ".jpg");
        Path targetPath = Paths.get("src/main/resources/static/photos/" + newPost.getPhotos().get(0) + ".jpg");
        Files.move(sourcePath, targetPath);
        return repository.save(newPost);
    }

    @PutMapping("/{id}")
    Post editCaption(@PathVariable Long id, @RequestParam String newCaption) {
        Post post = getById(id);
        post.setCaption(newCaption);
        return repository.save(post);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
