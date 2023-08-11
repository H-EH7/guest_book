package eh7.guestbook.service;

import eh7.guestbook.domain.Post;
import eh7.guestbook.exception.WrongConstException;
import eh7.guestbook.exception.WrongPasswordException;
import eh7.guestbook.repository.PostRepository;
import eh7.guestbook.repository.PostSearchCond;
import eh7.guestbook.repository.dto.PostUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static eh7.guestbook.domain.consts.RelationshipConst.*;
import static eh7.guestbook.domain.consts.SideConst.*;
import static org.assertj.core.api.Assertions.*;

@Slf4j
@Transactional
@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostRepository postRepository;

    @Test
    void post() {
        // given
        Post post = new Post("test", "1111", GROOM, FAMILY, "테스트입니다.");

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
        Post post = postRepository.save(new Post("test", "1111", GROOM, FAMILY, "테스트입니다."));
        PostUpdateDto updateDto = new PostUpdateDto("test1", "1111", GROOM, FRIEND, "변경된 콘텐츠입니다.");

        // when
        boolean result = postService.edit(post.getId(), updateDto);

        // then
        Post findPost = postRepository.findById(post.getId()).get();
        assertThat(findPost.getAuthor()).isEqualTo(updateDto.getAuthor());
        assertThat(findPost.getSide()).isEqualTo(updateDto.getSide());
        assertThat(findPost.getRelationship()).isEqualTo(updateDto.getRelationship());
        assertThat(findPost.getContent()).isEqualTo(updateDto.getContent());

        assertThat(result).isTrue();
    }

    @Test
    void findAll() {
        // given
        Post postA = postRepository.save(new Post("testA-1", "1111", GROOM, FAMILY, "테스트1입니다."));
        Post postB = postRepository.save(new Post("testB-1", "2222", BRIDE, ETC, "테스트2입니다."));
        Post postC = postRepository.save(new Post("testB-2", "3333", GROOM, COLLEAGUE, "테스트3입니다."));

        // 셋 다 없을 때
        findAllValidate("", "", "", postA, postB, postC);

        // author만 있을 때
        findAllValidate("testB", "", "", postB, postC);
        // side만 있을 때
        findAllValidate("", GROOM, "", postA, postC);
        // relationship만 있을 때
        findAllValidate("", "", COLLEAGUE, postC);

        // author, side
        findAllValidate("testB", BRIDE, "", postB);
        // author, relationship
        findAllValidate("testA", "", FAMILY, postA);
        // side, relationship
        findAllValidate("", BRIDE, ETC, postB);

        // 셋 다 있을 때
        findAllValidate("testB", GROOM, COLLEAGUE, postC);

        // 없는 값을 찾을 때
        findAllValidate("testC", BRIDE, FRIEND);
    }

    void findAllValidate(String author, String side, String relationship, Post... posts) {
        List<Post> result = postService.findAll(new PostSearchCond(author, side, relationship));
        assertThat(result).containsExactly(posts);
    }

    @Test
    void delete() {
        // given
        Post post = postRepository.save(new Post("test", "1111", GROOM, FAMILY, "테스트입니다."));

        // when
        boolean result = postService.delete(post.getId(), "1111");

        // then
        Optional<Post> findPost = postRepository.findById(post.getId());
        assertThat(findPost.isEmpty()).isTrue();
        assertThat(result).isTrue();
    }

    @Test
    void passwordValidation() {
        // given
        Post post = postRepository.save(new Post("test", "1111", GROOM, FAMILY, "테스트입니다."));

        // 패스워드가 틀릴 경우
        // 수정 시 확인
        assertThatThrownBy(() -> {
            postService.edit(post.getId(), new PostUpdateDto("test2", "2222", BRIDE, FRIEND, "변경 테스트입니다."));
        }).isInstanceOf(WrongPasswordException.class);
        // 삭제 시 확인
        assertThatThrownBy(() -> {
            postService.delete(post.getId(), "2222");
        }).isInstanceOf(WrongPasswordException.class);
    }

    @Test
    void constValidation() {
        // given
        Post post = postRepository.save(new Post("test", "1111", GROOM, FAMILY, "테스트입니다."));

        // 틀린 상수 값을 사용한 경우
        // 저장 시 확인
        // Side
        assertThatThrownBy(() -> {
            postService.post(new Post("test1", "1111", "WrongSide", FAMILY, "저장 테스트입니다."));
        }).isInstanceOf(WrongConstException.class);
        // Relationship
        assertThatThrownBy(() -> {
            postService.post(new Post("test2", "1111", GROOM, "WrongRelationship", "저장 테스트입니다."));
        }).isInstanceOf(WrongConstException.class);

        // 수정 시 확인
        // Side
        assertThatThrownBy(() -> {
            postService.edit(post.getId(), new PostUpdateDto("testA", "1111", "WrongSide", FRIEND, "수정 테스트입니다."));
        }).isInstanceOf(WrongConstException.class);
        // Relationship
        assertThatThrownBy(() -> {
            postService.edit(post.getId(), new PostUpdateDto("testB", "1111", BRIDE, "WrongRelationship", "수정 테스트입니다."));
        }).isInstanceOf(WrongConstException.class);
    }
}