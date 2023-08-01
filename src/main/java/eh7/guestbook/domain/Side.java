package eh7.guestbook.domain;

import lombok.Getter;

import java.util.Arrays;

/**
 * 신랑, 신부 중 어느 측인지
 */
@Getter
public enum Side {
    GROOM("신랑"),
    BRIDE("신부");

    private final String label;

    Side(String label) {
        this.label = label;
    }

    /**
     * label로 enum값 찾기
     * @param label
     * @return
     */
    public static Side valueOfLabel(String label) {
        return Arrays.stream(values())
                .filter(value -> value.label.equals(label))
                .findAny()
                .orElse(null);
    }
}
