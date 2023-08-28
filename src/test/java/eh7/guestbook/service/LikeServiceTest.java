package eh7.guestbook.service;

import eh7.guestbook.domain.Like;
import eh7.guestbook.domain.Post;
import eh7.guestbook.repository.LikeRepository;
import eh7.guestbook.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import static eh7.guestbook.domain.consts.RelationshipConst.*;
import static eh7.guestbook.domain.consts.SideConst.*;
import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class LikeServiceTest {

    @Autowired
    LikeService likeService;
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    PostRepository postRepository;

    @Test
    void alreadyLike() {
        // given
        Post post = new Post("test", "1111", GROOM, FAMILY, "test content");
        Post savedPost = postRepository.save(post);

        String addr1 = "0000:0000:0000:0000:0000:0000:0000:1111";
        String addr2 = "0000:0000:0000:0000:0000:0000:0000:2222";
        Like like = new Like(savedPost.getId(), addr1);
        Like savedLike = likeRepository.save(like);

        // when
        boolean result1 = likeService.alreadyLike(savedPost.getId(), addr1);
        boolean result2 = likeService.alreadyLike(savedPost.getId(), addr2);

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    void like() {
        // given
        Post post = new Post("test", "1111", GROOM, FAMILY, "test content");
        Post savedPost = postRepository.save(post);

        String addr = "0000:0000:0000:0000:0000:0000:0000:1111";

        // when
        Like savedLike = likeService.like(savedPost.getId(), addr);

        // then
        Post findPost = postRepository.findById(savedPost.getId()).get();
        assertThat(findPost.getLikes()).isEqualTo(1L);

        Like findLike = likeRepository.findById(savedLike.getId());
        assertThat(findLike.getPostId()).isEqualTo(findPost.getId());
        assertThat(findLike.getAddress()).isEqualTo(addr);
    }

    @Test
    void unlike() {
        // given
        Post post = new Post("test", "1111", GROOM, FAMILY, "test content");
        Post savedPost = postRepository.save(post);

        String addr = "0000:0000:0000:0000:0000:0000:0000:1111";
        Like savedLike = likeService.like(savedPost.getId(), addr);

        // when
        likeService.unlike(savedLike.getId(), addr);

        // then
        assertThatThrownBy(() -> {
            likeRepository.findById(savedLike.getId());
        }).isInstanceOf(EmptyResultDataAccessException.class);

        Post findPost = postRepository.findById(savedPost.getId()).get();
        assertThat(findPost.getLikes()).isEqualTo(0L);
    }
}
