package com.cliente.escola.gradecurricular.handler;

import com.cliente.escola.gradecurricular.exception.MateriaException;
import com.cliente.escola.gradecurricular.model.ErrorMapResponse;
import com.cliente.escola.gradecurricular.model.ErrorMapResponse.ErrorMapResponseBuilder;
import com.cliente.escola.gradecurricular.model.ErrorResponse;
import com.cliente.escola.gradecurricular.model.ErrorResponse.ErrorResponseBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ResourceHandler {
    /**
     * Quando chamar a exception MateriaException ele dará esse tratamento
     */
    @ExceptionHandler(MateriaException.class)
    public ResponseEntity<ErrorResponse> handlerMateriaException(MateriaException m) {
        ErrorResponseBuilder erro = ErrorResponse.builder();
        erro.HttpStatus(m.getHttpStatus().value());
        erro.mensagem(m.getMessage());
        erro.timeStamp(System.currentTimeMillis());

        return ResponseEntity.status(m.getHttpStatus()).body(erro.build());

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMapResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException m) {
        Map<String, String> erros = new HashMap<>();

        /*Busca erro e salva o fild e a mensagem de erro*/
        m.getBindingResult().getAllErrors().forEach(erro -> {
            String campo = ((FieldError) erro).getField();
            String mensagem = erro.getDefaultMessage();
            erros.put(campo, mensagem);
        });

        ErrorMapResponseBuilder errorMap = ErrorMapResponse.builder();
        errorMap.erros(erros).httpStatus(HttpStatus.BAD_REQUEST.value())
                .timeStamp(System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.MULTI_STATUS.BAD_REQUEST).body(errorMap.build());

    }

}
