package eh7.guestbook.controller;

import eh7.guestbook.domain.Like;
import eh7.guestbook.domain.Post;
import eh7.guestbook.repository.PostSearchCond;
import eh7.guestbook.service.LikeService;
import eh7.guestbook.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manage")
@RequiredArgsConstructor
public class ViewController {

    private final PostService postService;
    private final LikeService likeService;

    @GetMapping
    public String postManagePage(Model model) {
        List<Post> findPosts = postService.findAll(new PostSearchCond());
        model.addAttribute("posts", findPosts);
        return "post-manage";
    }

    @GetMapping("/{postId}")
    public String likeManagePage(Model model, @PathVariable Long postId) {
        List<Like> findLikes = likeService.findAllByPostId(postId);
        model.addAttribute("likes", findLikes);
        return "like-manage";
    }

    @GetMapping("/crud")
    public String postCrudPage() {
        return "post-crud";
    }
}
