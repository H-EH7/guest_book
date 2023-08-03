package eh7.guestbook.service;

import eh7.guestbook.domain.Post;
import eh7.guestbook.domain.consts.RelationshipConst;
import eh7.guestbook.domain.consts.SideConst;
import eh7.guestbook.repository.PostRepository;
import eh7.guestbook.repository.PostSearchCond;
import eh7.guestbook.repository.dto.PostUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;

    @Test
    void post() {
        // given
        Post post = new Post("test", "1111", SideConst.GROOM, RelationshipConst.FAMILY, "테스트입니다.");

        // when
        Post result = postService.post(post);

        // then
        Post findPost = postRepository.findById(result.getId()).get();
        log.info("POST TEST");
        log.info("post={}", findPost);
        log.info("result={}", result);
        assertThat(result).isEqualTo(findPost);
    }

    @Test
    void edit() {
        // given
        Post post = postRepository.save(new Post("test", "1111", SideConst.GROOM, RelationshipConst.FAMILY, "테스트입니다."));
        PostUpdateDto updateDto = new PostUpdateDto("test1", SideConst.GROOM, RelationshipConst.FRIEND, "변경된 콘텐츠입니다.");
        PostUpdateDto updateDtoFalse = new PostUpdateDto("test2", SideConst.BRIDE, RelationshipConst.ETC, "비밀번호가 틀린 경우입니다.");
        updateDto.setPassword("1111");
        updateDtoFalse.setPassword("2222");

        // when
        boolean resultA = postService.edit(post.getId(), updateDto);
        boolean resultB = postService.edit(post.getId(), updateDtoFalse);

        // then
        Post findPost = postRepository.findById(post.getId()).get();
        assertThat(findPost.getAuthor()).isEqualTo(updateDto.getAuthor());
        assertThat(findPost.getSide()).isEqualTo(updateDto.getSide());
        assertThat(findPost.getRelationship()).isEqualTo(updateDto.getRelationship());
        assertThat(findPost.getContent()).isEqualTo(updateDto.getContent());

        assertThat(resultA).isTrue();
        assertThat(resultB).isFalse();
    }

    @Test
    void findAll() {
        // given
        Post postA = postRepository.save(new Post("testA-1", "1111", SideConst.GROOM, RelationshipConst.FAMILY, "테스트1입니다."));
        Post postB = postRepository.save(new Post("testB-1", "2222", SideConst.BRIDE, RelationshipConst.ETC, "테스트2입니다."));
        Post postC = postRepository.save(new Post("testB-2", "3333", SideConst.GROOM, RelationshipConst.COLLEAGUE, "테스트3입니다."));

        // 셋 다 없을 때
        findAllValidate("", "", "", postA, postB, postC);

        // author만 있을 때
        findAllValidate("testB", "", "", postB, postC);
        // side만 있을 때
        findAllValidate("", SideConst.GROOM, "", postA, postC);
        // relationship만 있을 때
        findAllValidate("", "", RelationshipConst.COLLEAGUE, postC);

        // author, side
        findAllValidate("testB", SideConst.BRIDE, "", postB);
        // author, relationship
        findAllValidate("testA", "", RelationshipConst.FAMILY, postA);
        // side, relationship
        findAllValidate("", SideConst.BRIDE, RelationshipConst.ETC, postB);

        // 셋 다 있을 때
        findAllValidate("testB", SideConst.GROOM, RelationshipConst.COLLEAGUE, postC);

        // 없는 값을 찾을 때
        findAllValidate("testC", SideConst.BRIDE, RelationshipConst.FRIEND);
    }

    void findAllValidate(String author, String side, String relationship, Post... posts) {
        List<Post> result = postService.findAll(new PostSearchCond(author, side, relationship));
        assertThat(result).containsExactly(posts);
    }

    @Test
    void delete() {
        // given
        Post post = postRepository.save(new Post("test", "1111", SideConst.GROOM, RelationshipConst.FAMILY, "테스트입니다."));

        // when
        boolean resultA = postService.delete(post.getId(), "2222");
        boolean resultB = postService.delete(post.getId(), "1111");

        // then
        Optional<Post> findPost = postRepository.findById(post.getId());
        assertThat(findPost.isEmpty()).isTrue();
        assertThat(resultA).isFalse();
        assertThat(resultB).isTrue();
    }
}