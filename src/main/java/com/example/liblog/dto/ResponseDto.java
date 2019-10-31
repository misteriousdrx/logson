package com.example.liblog.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseDto {

    @JsonProperty("codigo_retorno")
    String returnCode;

    @JsonProperty("mensagem_retorno")
    String returnMessage;

}
