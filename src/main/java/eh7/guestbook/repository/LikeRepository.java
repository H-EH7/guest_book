package eh7.guestbook.repository;

import eh7.guestbook.domain.Like;

import java.util.List;

public interface LikeRepository {

    Like save(Like like);

    Like findById(Long likeId);

    List<Like> findByPostId(Long postId);

    void delete(Long likeId);
}
