package eh7.guestbook.domain;

import lombok.Data;

/**
 * 게시글 클래스
 */
@Data
public class Post {

    private Long id;            // PK
    private String author;      // 작성자
    private String password;    // 글 비밀번호
    private String side;          // 어느 측(신랑/신부)인지
    private String relationship; // 관계
    private String content;     // 글 내용
    private Long likes;         // 좋아요

    public Post() {
    }

    public Post(String author, String password, String side, String relationship, String content) {
        this.author = author;
        this.password = password;
        this.side = side;
        this.relationship = relationship;
        this.content = content;
        this.likes = 0L;
    }
}