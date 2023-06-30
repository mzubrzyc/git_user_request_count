package app.git.exceptions;

import app.git.common.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message> handleException(Exception e) {
        return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
