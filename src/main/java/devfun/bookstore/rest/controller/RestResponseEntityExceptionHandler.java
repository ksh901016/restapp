package devfun.bookstore.rest.controller;

import devfun.bookstore.rest.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request){
        String bodyOfResponse = "해당 자원을 찾을 수 없습니다.";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), null, request);
    }
}
