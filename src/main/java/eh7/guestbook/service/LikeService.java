package eh7.guestbook.service;

import eh7.guestbook.domain.Like;
import eh7.guestbook.domain.Post;
import eh7.guestbook.repository.LikeRepository;
import eh7.guestbook.repository.PostRepository;
import eh7.guestbook.repository.dto.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;

    // TODO: Controller에서 alreadyLike()를 통해 확인하고 like, unlike 중 무엇을 호출할지 결정
    public boolean alreadyLike(Long postId, String address) {
        List<Like> findResult = likeRepository.findByPostId(postId);
        if (findResult.isEmpty()) {
            // 좋아요가 0인 상태
            return false;
        }

        // 좋아요가 있다면
        // 해당 ip에서 누른적이 있는지 확인
        for (Like like : findResult) {
            if (like.getAddress().equals(address)) {
                return true;
            }
        }

        // 누른적 없다면 false 반환
        return false;
    }

    @Transactional
    public Like like(Long postId, String address) {
        Post post = postRepository.findById(postId).get();
        Like like = new Like(postId, address);

        Like savedLike = likeRepository.save(like);

        PostUpdateDto postUpdateDto = createPostUpdateDto(post);
        postUpdateDto.setLikes(post.getLikes() + 1L);

        postRepository.update(postId, postUpdateDto);

        return savedLike;
    }

    @Transactional
    public void unlike(Long postId, String address) {
        Post post = postRepository.findById(postId).get();
        List<Like> likes = likeRepository.findByPostId(postId);

        Long likeId = likes.stream().filter((like) -> {
            return like.getAddress().equals(address);
        }).findAny().get().getId();
        likeRepository.delete(likeId);

        PostUpdateDto postUpdateDto = createPostUpdateDto(post);
        postUpdateDto.setLikes(post.getLikes() - 1L);

        postRepository.update(postId, postUpdateDto);
    }

    private PostUpdateDto createPostUpdateDto(Post post) {
        PostUpdateDto postUpdateDto = new PostUpdateDto();
        postUpdateDto.setAuthor(post.getAuthor());
        postUpdateDto.setPassword(post.getPassword());
        postUpdateDto.setSide(post.getSide());
        postUpdateDto.setRelationship(post.getRelationship());
        postUpdateDto.setContent(post.getContent());
        return postUpdateDto;
    }
}
