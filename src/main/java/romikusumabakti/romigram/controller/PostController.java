package romikusumabakti.romigram.controller;

import romikusumabakti.romigram.model.Account;
import romikusumabakti.romigram.model.Comment;
import romikusumabakti.romigram.model.Post;
import romikusumabakti.romigram.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    private final PostRepository repository;

    public PostController(PostRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/posts")
    List<Post> getAll() {
        return repository.findAll();
    }

    @GetMapping("/posts/{id}")
    Post getById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(RuntimeException::new);
    }

    @GetMapping("/posts/{id}/likes")
    List<Account> getLikes(@PathVariable Long id) {
        return getById(id).getLikes();
    }

    @GetMapping("/posts/{id}/comments")
    List<Comment> getComments(@PathVariable Long id) {
        return getById(id).getComments();
    }

    @PostMapping("/posts/{id}/like")
    void like(@PathVariable Long id) {
        repository.findById(id)
                .map(post -> {
                    post.getLikes().add(new Account());
                    return null;
                });
    }

    @PostMapping("/posts")
    Post create(@RequestBody Post newPost) {
        return repository.save(newPost);
    }

    @PutMapping("/posts/{id}")
    Post editCaption(@PathVariable Long id, @RequestParam String newCaption) {
        return repository.findById(id)
                .map(post -> {
                    post.setCaption(newCaption);
                    return repository.save(post);
                }).orElse(null);
    }

    @DeleteMapping("/posts/{id}")
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
