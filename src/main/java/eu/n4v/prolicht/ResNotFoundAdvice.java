package eu.n4v.prolicht;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ResNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String resNotFoundHandler(ResNotFoundException ex) {
        return ex.getMessage();
    }
}
