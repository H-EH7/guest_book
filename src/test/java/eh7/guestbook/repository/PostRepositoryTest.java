package eh7.guestbook.repository;

import eh7.guestbook.domain.Post;
import eh7.guestbook.domain.consts.RelationshipConst;
import eh7.guestbook.domain.consts.SideConst;
import eh7.guestbook.exception.IllegalIdException;
import eh7.guestbook.repository.dto.PostUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Post post1 = new Post("test1", "test111", SideConst.GROOM, RelationshipConst.FAMILY, "테스트1입니다.");
        Post post2 = new Post("test2", "test222", SideConst.BRIDE, RelationshipConst.FRIEND, "테스트2입니다.");

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
        Post post = new Post("test1", "test111", SideConst.GROOM, RelationshipConst.FAMILY, "테스트입니다..");
        Post savedPost = postRepository.save(post);
        Long postId = savedPost.getId();

        // when
        PostUpdateDto updateDto = new PostUpdateDto("changed", "test111", SideConst.BRIDE, RelationshipConst.ETC, "변경된 콘텐츠입니다.");
        postRepository.update(postId, updateDto);

        // then
        Post findPost = postRepository.findById(postId).get();
        log.info("UPDATE TEST");
        log.info("post = {}", savedPost);
        log.info("updated post = {}", findPost);
        assertThat(findPost.getAuthor()).isEqualTo(updateDto.getAuthor());
        assertThat(findPost.getSide()).isEqualTo(updateDto.getSide());
        assertThat(findPost.getRelationship()).isEqualTo(updateDto.getRelationship());
        assertThat(findPost.getContent()).isEqualTo(updateDto.getContent());
    }

    @Test
    void findAll() {
        // given
        Post post1 = new Post("userA-1", "1111", SideConst.GROOM, RelationshipConst.FAMILY, "테스트입니다.");
        Post post2 = new Post("userA-2", "1111", SideConst.BRIDE, RelationshipConst.FRIEND, "테스트입니다.");
        Post post3 = new Post("userB-1", "1111", SideConst.GROOM, RelationshipConst.ETC, "테스트입니다.");

        postRepository.save(post1);
        postRepository.save(post2);
        postRepository.save(post3);

        // 셋 다 없을 때
        findAllValidate("", "", "", post1, post2, post3);

        // author만 있을 때
        findAllValidate("userA", "", "", post1, post2);
        // side만 있을 때
        findAllValidate("", SideConst.GROOM, "", post1, post3);
        // relationship만 있을 때
        findAllValidate("", "", RelationshipConst.FRIEND, post2);

        // author, side
        findAllValidate("userA", SideConst.BRIDE, "", post2);
        // author, relationship
        findAllValidate("userA", "", RelationshipConst.FAMILY, post1);
        // side, relationship
        findAllValidate("", SideConst.GROOM, RelationshipConst.ETC, post3);

        // 셋 다 있을 때
        findAllValidate("userA", SideConst.GROOM, RelationshipConst.FAMILY, post1);

        // 없는 값을 찾을 때
        findAllValidate("userC", "", "");
    }

    void findAllValidate(String author, String side, String relationship, Post... posts) {
        List<Post> result = postRepository.findAll(new PostSearchCond(author, side, relationship));
        assertThat(result).containsExactly(posts);
    }

    @Test
    void delete() {
        // given
        Post post = new Post("test1", "test111", SideConst.GROOM, RelationshipConst.FAMILY, "테스트입니다.");
        Post savedPost = postRepository.save(post);
        Long postId = savedPost.getId();

        // when
        postRepository.delete(postId);

        // then
        assertThatThrownBy(() -> {
            postRepository.findById(postId);
        }).isInstanceOf(IllegalIdException.class);
    }
}
