package eh7.guestbook.controller.advice;

import eh7.guestbook.exception.ErrorResult;
import eh7.guestbook.exception.IllegalConstException;
import eh7.guestbook.exception.IllegalIdException;
import eh7.guestbook.exception.IllegalPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PostControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult constExHandle(IllegalConstException e) {
        return new ErrorResult("BAD", "잘못된 Const 값");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult passwordExHandle(IllegalPasswordException e) {
        return new ErrorResult("BAD", "잘못된 비밀번호입니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public ErrorResult idExHandle(IllegalIdException e) {
        return new ErrorResult("BAD", "찾을 수 없는 게시물입니다.");
    }
}
