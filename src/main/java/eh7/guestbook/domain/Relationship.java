package eh7.guestbook.domain;

import lombok.Getter;

import java.util.Arrays;

/**
 * 관계가 어떻게 되는지
 */
@Getter
public enum Relationship {
    FAMILY("가족"),
    FRIEND("친구"),
    COLLEAGUE("직장동료"),
    ETC("지인");

    private final String label;

    Relationship(String label) {
        this.label = label;
    }

    /**
     * label로 enum값 찾기
     * @param label
     * @return
     */
    public static Relationship valueOfLabel(String label) {
        return Arrays.stream(values())
                .filter(value -> value.label.equals(label))
                .findAny()
                .orElse(null);
    }
}
