package eh7.guestbook.repository.dto;

import eh7.guestbook.domain.Post;
import lombok.Data;

/**
 * 데이터 저장 시 DTO 클래스
 */
@Data
public class PostSaveDto {

    private String author;      // 작성자
    private String password;    // 글 비밀번호
    private String side;        // 어느 측(신랑/신부)인지
    private String relationship;// 관계
    private String content;     // 글 내용

    /**
     * Post 객체의 Enum 값들을 String 값으로 변환하여 PostSaveDto를 생성
     * @param post
     */
    public PostSaveDto(Post post) {
        this.author = post.getAuthor();
        this.password = post.getPassword();
        this.side = post.getSide().getLabel();
        this.relationship = post.getRelationship().getLabel();
        this.content = post.getContent();
    }
}
