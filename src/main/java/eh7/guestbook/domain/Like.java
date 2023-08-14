package eh7.guestbook.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Like {
    private Long id;
    private Long postId;
    private String address;

    public Like(Long postId, String address) {
        this.postId = postId;
        this.address = address;
    }
}
