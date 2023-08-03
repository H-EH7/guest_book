package eh7.guestbook.repository;

import lombok.Data;

/**
 * 게시글 검색을 위한 조건
 */
@Data
public class PostSearchCond {

    private String author;      // 작성자
    private String side;     // 어느 측(신랑/신부)인지
    private String relationship; // 관계

    public PostSearchCond() {
    }

    public PostSearchCond(String author, String side, String relationship) {
        this.author = author;
        this.side = side;
        this.relationship = relationship;
    }
}
