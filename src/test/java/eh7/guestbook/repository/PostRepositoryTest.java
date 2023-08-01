package eh7.guestbook.repository;

import eh7.guestbook.domain.Post;
import eh7.guestbook.domain.Relationship;
import eh7.guestbook.domain.Side;
import eh7.guestbook.repository.dto.PostUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

/**
 * DB는 임베디드 H2 DB를 사용
 */
@Slf4j
@Transactional
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void save() {
        // given
        Post post1 = new Post("test1", "test111", Side.GROOM, Relationship.FAMILY, "테스트1입니다.");
        Post post2 = new Post("test2", "test222", Side.BRIDE, Relationship.FRIEND, "테스트2입니다.");

        // when
        Post savedPost1 = postRepository.save(post1);
        Post savedPost2 = postRepository.save(post2);

        // then
        log.info("SAVE TEST");
        log.info("post1 = {}", savedPost1);
        log.info("post2 = {}", savedPost2);
        Post findPost1 = postRepository.findById(savedPost1.getId()).get();
        Post findPost2 = postRepository.findById(savedPost2.getId()).get();
        assertThat(findPost1).isEqualTo(savedPost1);
        assertThat(findPost2).isEqualTo(savedPost2);
    }

    @Test
    void update() {
        // given
        Post post = new Post("test1", "test111", Side.GROOM, Relationship.FAMILY, "테스트1입니다.");
        Post savedPost = postRepository.save(post);
        Long postId = savedPost.getId();

        // when
        PostUpdateDto updateDto = new PostUpdateDto("changed", Side.BRIDE.getLabel(), Relationship.ETC.getLabel(), "변경된 콘텐츠입니다.");
        postRepository.update(postId, updateDto);

        // then
        Post findPost = postRepository.findById(postId).get();
        log.info("UPDATE TEST");
        log.info("post = {}", savedPost);
        log.info("updated post = {}", findPost);
        assertThat(findPost.getAuthor()).isEqualTo(updateDto.getAuthor());
        assertThat(findPost.getSide().getLabel()).isEqualTo(updateDto.getSide());
        assertThat(findPost.getRelationship().getLabel()).isEqualTo(updateDto.getRelationship());
        assertThat(findPost.getContent()).isEqualTo(updateDto.getContent());
    }
}
