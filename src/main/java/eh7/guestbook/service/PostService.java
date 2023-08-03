package eh7.guestbook.service;

import eh7.guestbook.domain.Post;
import eh7.guestbook.repository.PostRepository;
import eh7.guestbook.repository.PostSearchCond;
import eh7.guestbook.repository.dto.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// TODO: 비밀번호 검증에서 boolean을 쓰는게 맞는지? -> 예외로 변경해야 하는지 검토할 것
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    // TODO: side와 relationship이 올바른 값인지 검증할 것
    public Post post(Post post) {
        return postRepository.save(post);
    }

    public boolean edit(Long postId, PostUpdateDto updateDto) {
        // 비밀번호가 올바른지 확인
        Post findPost = postRepository.findById(postId).get();

        // 비밀번호가 맞으면 수정
        if (findPost.getPassword().equals(updateDto.getPassword())) {
            postRepository.update(postId, updateDto);
            return true;
        }

        // 비밀번호가 틀리면 수정 X
        return false;
    }

    public List<Post> findAll(PostSearchCond cond) {
        return postRepository.findAll(cond);
    }

    public boolean delete(Long postId, String password) {
        // 비밀번호가 올바른지 확인
        Optional<Post> findPostOptional = postRepository.findById(postId);

        // 존재하지 않는 id일 경우
        if (findPostOptional.isEmpty()) {
            return false;
        }

        Post findPost = findPostOptional.get();

        if (findPost.getPassword().equals(password)) {
            // 비밀번호가 맞으면 삭제
            return postRepository.delete(postId);
        }

        // 비밀번호가 틀리면 삭제 X
        return false;
    }
}
