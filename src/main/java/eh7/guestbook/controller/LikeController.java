package eh7.guestbook.controller;

import eh7.guestbook.service.LikeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{postId}")
    public void like(@PathVariable Long postId, HttpServletRequest request) {
        String addr = request.getLocalAddr();
        if (likeService.alreadyLike(postId, addr)) {
            likeService.unlike(postId, addr);
        } else {
            likeService.like(postId, addr);
        }
    }
}
