package eh7.guestbook.repository.dto;

import eh7.guestbook.domain.Post;
import lombok.Data;

/**
 * 게시글 수정 시 DTO 클래스
 */
@Data
public class PostUpdateDto {

    private String author;      // 작성자
    private String side;        // 어느 측(신랑/신부)인지
    private String relationship;// 관계
    private String content;     // 글 내용

    /**
     * Post 객체의 Enum 값들을 String 값으로 변환하여 PostUpdateDto를 생성
     * @param post
     */
    public PostUpdateDto(Post post) {
        this.author = post.getAuthor();
        this.side = post.getSide().getLabel();
        this.relationship = post.getRelationship().getLabel();
        this.content = post.getContent();
    }
}
