package eh7.guestbook.repository;

import eh7.guestbook.domain.Relationship;
import eh7.guestbook.domain.Side;
import lombok.Data;

/**
 * 게시글 검색을 위한 조건
 */
@Data
public class PostSearchCond {

    private String author;      // 작성자
    private Side side;     // 어느 측(신랑/신부)인지
    private Relationship relationship; // 관계

    public PostSearchCond() {
    }
}
