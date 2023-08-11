package eh7.guestbook.repository;

import eh7.guestbook.domain.Post;
import eh7.guestbook.repository.dto.PostUpdateDto;

import java.util.List;
import java.util.Optional;

/**
 * 게시글 저장소 인터페이스
 */
public interface PostRepository {

    /**
     * 새 게시글 저장
     * @param post 저장할 게시글 객체
     * @return 저장 완료된 게시글 객체 반환
     */
    Post save(Post post);

    /**
     * 기존 게시글 수정
     * @param postId 수정할 게시글의 ID값
     * @param updateDto 수정할 게시글의 내용
     */
    void update(Long postId, PostUpdateDto updateDto);

    /**
     * 게시글 조회
     */
    Optional<Post> findById(Long postId);

    /**
     * 조건에 맞는 게시글 조회
     * @param cond 게시글 조회를 위한 조건
     * @return 조회한 게시글을 리스트 형태로 반환
     */
    List<Post> findAll(PostSearchCond cond);

    /**
     * 게시글 삭제
     *
     * @param postId 삭제할 게시글의 ID
     * @return 삭제 성공 여부 반환
     */
    void delete(Long postId);

}
