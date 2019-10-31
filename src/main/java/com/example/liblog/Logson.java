package com.example.liblog;

import com.example.liblog.dto.ResponseDto;
import com.example.liblog.enums.Severity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Logson {


    private static ResponseDto responseDto = new ResponseDto();



    public static void setCorrelationId(String correlationId) {

        MDC.put("correlationId", correlationId);

    }

    public static String getCorrelationId() {

        return MDC.get("correlationId");

    }


    public static void warning(String message) {

        Logger logger = LoggerFactory.getLogger(Logson.class);

        responseDto.setReturnCode("00");
        responseDto.setReturnMessage("It works!");

        String data = getBasicInfoAsMap(Severity.WARN, message, Optional.ofNullable(responseDto), Optional.empty());

        logger.warn(data);

//        logger.warn("{ \"severity\" : \"WARN\",  \"correlationId\" : \"{}\", \"message\" : \"{}\" }", getCorrelationId(), message);

    }

    public static void error(String message) {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        Logger logger = LoggerFactory.getLogger(Logson.class);

        logger.error("{ \"severity\" : \"INFO\",  \"correlationId\" : \"{}\", \"message\" : \"{}\" }", getCorrelationId(), message);

    }

    public static void info(String message) {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        Logger logger = LoggerFactory.getLogger(Logson.class);

        logger.info("{ \"severity\" : \"INFO\",  \"correlationId\" : \"{}\", \"message\" : \"{}\" }", getCorrelationId(), message);

    }

    public static void debug(String message) {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        Logger logger = LoggerFactory.getLogger(Logson.class);

        logger.debug("{ \"severity\" : \"DEBUG\", \"correlationId\" : \"{}\", \"message\" : \"{}\" }", getCorrelationId(), message);

    }

    public static void trace(String message) {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        Logger logger = LoggerFactory.getLogger(Logson.class);

        logger.trace("{ \"severity\" : \"DEBUG\", \"correlationId\" : \"{}\", \"message\" : \"{}\" }", getCorrelationId(), message);

    }

    private static String getBasicInfoAsMap(Severity severity, String message, Optional<Object> payload, Optional<Throwable> exception){

        try {

            Map<String, Object> data = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();
            Gson gson = new Gson();

            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];

            data.put("severity", severity.getValue());
            data.put("correlationId", getCorrelationId());
            data.put("codePoint", stackTraceElement.getClassName().concat(" : ").concat(String.valueOf(stackTraceElement.getLineNumber())));
            data.put("message", message);

            payload.ifPresent(p -> data.put("payload", p));
            exception.ifPresent(e -> data.put("thrownException", e));

            return mapper.writeValueAsString(data);
//            return gson.toJson(data);

        } catch (JsonProcessingException e) {
            return "Erro ao serializar objeto. ".concat(e.toString());

        }

    }

}
