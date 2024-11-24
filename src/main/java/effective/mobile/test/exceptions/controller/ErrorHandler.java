package effective.mobile.test.exceptions.controller;

import effective.mobile.test.exceptions.dto.ErrorResponse;
import effective.mobile.test.exceptions.model.BadRequestException;
import effective.mobile.test.exceptions.model.ConflictRequestException;
import effective.mobile.test.exceptions.model.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static effective.mobile.test.constants.Constants.DATE_FORMAT;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);


    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final NotFoundException e) {
        log.warn("404 {}", e.getMessage(), e);
        return new ErrorResponse("Not found",
                e.getMessage(),
                "Not found",
                HttpStatus.NOT_FOUND.toString(),
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectRequest(final BadRequestException e) {
        log.warn("400 {}", e.getMessage(), e);
        return new ErrorResponse("Bad request",
                e.getMessage(),
                "Bad request",
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleIncorrectRequest(final ConflictRequestException e) {
        log.warn("409 {}", e.getMessage(), e);
        return new ErrorResponse("Conflict",
                e.getMessage(),
                "Conflict",
                HttpStatus.CONFLICT.toString(),
                LocalDateTime.now().format(formatter));
    }
}