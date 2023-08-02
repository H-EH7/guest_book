package eh7.guestbook.domain;

import lombok.Data;

// TODO: side와 relationship을 Enum으로 사용하는게 맞는지? -> 해결 방안 생각
/**
 * 게시글 클래스
 */
@Data
public class Post {

    private Long id;            // PK
    private String author;      // 작성자
    private String password;    // 글 비밀번호
    private Side side;          // 어느 측(신랑/신부)인지
    private Relationship relationship; // 관계
    private String content;     // 글 내용

    public Post() {
    }

    public Post(String author, String password, Side side, Relationship relationship, String content) {
        this.author = author;
        this.password = password;
        this.side = side;
        this.relationship = relationship;
        this.content = content;
    }
}