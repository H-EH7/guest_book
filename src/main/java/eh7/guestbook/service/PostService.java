package eh7.guestbook.service;

import eh7.guestbook.domain.Post;
import eh7.guestbook.repository.PostRepository;
import eh7.guestbook.repository.PostSearchCond;
import eh7.guestbook.repository.dto.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO: 비밀번호 검증에서 boolean을 쓰는게 맞는지? -> 예외로 변경해야 하는지 검토할 것
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Post post(Post post) {
        return postRepository.save(post);
    }

    public boolean edit(Long postId, PostUpdateDto updateDto, String password) {
        // 비밀번호가 올바른지 확인
        Post findPost = postRepository.findById(postId).get();

        // 비밀번호가 틀리면 수정 X
        if (findPost.getPassword() != password) {
            return false;
        }

        // 비밀번호가 맞으면 수정
        postRepository.update(postId, updateDto);
        return true;
    }

    public List<Post> findAll(PostSearchCond cond) {
        return postRepository.findAll(cond);
    }

    public boolean delete(Long postId, String password) {
        // 비밀번호가 올바른지 확인
        Post findPost = postRepository.findById(postId).get();

        // 비밀번호가 틀리면 삭제 X
        if (findPost.getPassword() != password) {
            return false;
        }

        // 비밀번호가 맞으면 삭제
        return postRepository.delete(postId);
    }
}
