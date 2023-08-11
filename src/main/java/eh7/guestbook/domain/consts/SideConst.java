package eh7.guestbook.domain.consts;

import java.util.List;

public class SideConst {
    public static final String GROOM = "신랑";
    public static final String BRIDE = "신부";

    private static final List list = List.of(GROOM, BRIDE);

    public static boolean notContain(String text) {
        return list.stream().noneMatch(param -> {
            return param.equals(text);
        });
    }
}
