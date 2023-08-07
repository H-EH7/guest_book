package eh7.guestbook.service;

import eh7.guestbook.domain.Post;
import eh7.guestbook.exception.WrongPasswordException;
import eh7.guestbook.repository.PostRepository;
import eh7.guestbook.repository.PostSearchCond;
import eh7.guestbook.repository.dto.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    // TODO: side와 relationship이 올바른 값인지 검증할 것
    public Post post(Post post) {
        return postRepository.save(post);
    }

    public boolean edit(Long postId, PostUpdateDto updateDto) {
        try {
            passwordValidate(postId, updateDto.getPassword());
            postRepository.update(postId, updateDto);
            return true;
        } catch (WrongPasswordException e) {
            return false;
        }
    }

    public List<Post> findAll(PostSearchCond cond) {
        return postRepository.findAll(cond);
    }

    public boolean delete(Long postId, String password) {
        try {
            passwordValidate(postId, password);
            return postRepository.delete(postId);
        } catch (WrongPasswordException e) {
            return false;
        }
    }

    private void passwordValidate(Long postId, String password) {
        Post post = postRepository.findById(postId).get();
        if (!post.getPassword().equals(password)) {
            throw new WrongPasswordException();
        }
    }
}
