package com.cliente.escola.gradecurricular.v1.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MateriaException extends RuntimeException{

    private final HttpStatus httpStatus;

    public MateriaException(final String message,  final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
