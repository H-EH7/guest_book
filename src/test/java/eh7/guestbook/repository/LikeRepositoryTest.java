package eh7.guestbook.repository;

import eh7.guestbook.domain.Like;
import eh7.guestbook.domain.Post;
import eh7.guestbook.domain.consts.RelationshipConst;
import eh7.guestbook.domain.consts.SideConst;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class LikeRepositoryTest {
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    PostRepository postRepository;

    Long postId;

    @BeforeEach
    void beforeEach() {
        Post savedPost = postRepository.save(new Post("test1", "1111", SideConst.GROOM, RelationshipConst.FAMILY, "test content"));
        postId = savedPost.getId();
    }

    @Test
    void save() {
        // given
        String addr = "0000:0000:0000:0000:0000:0000:0000:1111";
        Like like = new Like(postId, addr);

        // when
        Like savedLike = likeRepository.save(like);

        // then
        Like findLike = likeRepository.findById(savedLike.getId());
        assertThat(savedLike).isEqualTo(findLike);
    }

    @Test
    void findByPostId() {
        // given
        String addr1 = "0000:0000:0000:0000:0000:0000:0000:1111";
        String addr2 = "0000:0000:0000:0000:0000:0000:0000:2222";
        Like like1 = likeRepository.save(new Like(postId, addr1));
        Like like2 = likeRepository.save(new Like(postId, addr2));

        // when
        List<Like> likes = likeRepository.findByPostId(postId);

        // then
        for (Like like : likes) {
            Like findLike = likeRepository.findById(like.getId());
            assertThat(like.getPostId()).isEqualTo(findLike.getPostId());
            assertThat(like.getAddress()).isEqualTo(findLike.getAddress());
        }
    }

    @Test
    void delete() {
        // given
        String addr = "0000:0000:0000:0000:0000:0000:0000:1111";
        Like like = likeRepository.save(new Like(postId, addr));

        // when
        likeRepository.delete(like.getId());

        // then
        assertThatThrownBy(() -> {
            likeRepository.findById(like.getId());
        }).isInstanceOf(EmptyResultDataAccessException.class);
    }
}
