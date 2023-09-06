package eh7.guestbook.controller;

import eh7.guestbook.domain.Post;
import eh7.guestbook.repository.PostSearchCond;
import eh7.guestbook.repository.dto.PostUpdateDto;
import eh7.guestbook.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    /**
     * 게시글 조회:  GET '/posts'            조건: ?author=?&side=?&relationship
     * 게시글 작성:  POST '/posts'
     * 게시글 수정:  PATCH '/posts/{postId}'
     * 게시글 삭제:  DELETE '/posts/{postId}'
     */

    private final PostService postService;

    @GetMapping
    public List<Post> findPosts(@ModelAttribute PostSearchCond cond) {
        return postService.findAll(cond);
    }

    @PostMapping
    public Post post(Post post) {
        return postService.post(post);
    }

    @PatchMapping("/{postId}")
    public String editPost(@PathVariable Long postId, @RequestBody PostUpdateDto updateDto) {
        postService.edit(postId, updateDto);
        return "ok";
    }

    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable Long postId, @RequestBody String password) {
        postService.delete(postId, password);
        return "ok";
    }
}
