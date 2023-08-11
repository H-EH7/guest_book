package eh7.guestbook.domain.consts;

import java.util.List;

public class RelationshipConst {
    public static final String FAMILY = "가족";
    public static final String FRIEND = "친구";
    public static final String COLLEAGUE = "직장동료";
    public static final String ETC = "지인";

    private static final List list = List.of(FAMILY, FRIEND, COLLEAGUE, ETC);

    public static boolean notContain(String text) {
        return list.stream().noneMatch(param -> {
            return param.equals(text);
        });
    }
}
