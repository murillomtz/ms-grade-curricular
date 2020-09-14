package com.cliente.escola.gradecurricular.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {

    private String mensagem;
    private int HttpStatus;
    private long timeStamp;

}
