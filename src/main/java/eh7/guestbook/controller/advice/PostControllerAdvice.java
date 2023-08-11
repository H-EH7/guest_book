package eh7.guestbook.controller.advice;

import eh7.guestbook.exception.ErrorResult;
import eh7.guestbook.exception.WrongConstException;
import eh7.guestbook.exception.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult constExHandle(WrongConstException e) {
        return new ErrorResult("BAD", "잘못된 Const 값");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult passwordExHandle(WrongPasswordException e) {
        return new ErrorResult("BAD", "잘못된 비밀번호입니다.");
    }
}
