package eh7.guestbook.repository.dto;

import eh7.guestbook.domain.Post;
import lombok.Data;

/**
 * 게시글 수정 시 DTO 클래스
 */
@Data
public class PostUpdateDto {

    private String author;      // 작성자
    private String password;
    private String side;        // 어느 측(신랑/신부)인지
    private String relationship;// 관계
    private String content;     // 글 내용

    public PostUpdateDto() {
    }

    public PostUpdateDto(String author, String password, String side, String relationship, String content) {
        this.author = author;
        this.password = password;
        this.side = side;
        this.relationship = relationship;
        this.content = content;
    }
}
