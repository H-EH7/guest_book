package eh7.guestbook.service;

import eh7.guestbook.domain.Post;
import eh7.guestbook.domain.consts.RelationshipConst;
import eh7.guestbook.domain.consts.SideConst;
import eh7.guestbook.exception.WrongConstException;
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

    public Post post(Post post) {
        try {
            constValidate(post.getSide(), post.getRelationship());
            return postRepository.save(post);
        } catch (WrongConstException e) {
            return null;
        }
    }

    public boolean edit(Long postId, PostUpdateDto updateDto) {
        try {
            passwordValidate(postId, updateDto.getPassword());
            constValidate(updateDto.getSide(), updateDto.getRelationship());
            postRepository.update(postId, updateDto);
            return true;
        } catch (WrongPasswordException e) {
            return false;
        } catch (WrongConstException e) {
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

    private void constValidate(String side, String relationship) {
        if (SideConst.notContain(side)
                || RelationshipConst.notContain(relationship)) {
            throw new WrongConstException();
        }
    }
}
