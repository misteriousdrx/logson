package com.example.liblog;

import com.example.liblog.dto.ResponseDto;
import com.example.liblog.enums.Severity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Logson {


    private static Logger logger = LoggerFactory.getLogger(Logson.class);

    private static ResponseDto responseDto = new ResponseDto();
    private static String serviceId;
    private static String domain;



    public static void setCorrelationId(String correlationId) {

        MDC.put("correlationId", correlationId);

    }

    public static String getCorrelationId() {

        return MDC.get("correlationId");

    }

    public static void setServiceId(String serviceId) {
        Logson.serviceId = serviceId;
    }

    public static void setDomain(String domain) {
        Logson.domain = domain;
    }



    public static void error(String message) {

        responseDto.setReturnCode("02");
        responseDto.setReturnMessage("This is an error!");

        String correlation = getCorrelationId();
        new Thread(() -> doLog(Severity.ERROR, correlation, message, Optional.ofNullable(responseDto), Optional.empty())).start();


    }

    public static void error(String message, Throwable e) {

        responseDto.setReturnCode("02");
        responseDto.setReturnMessage("This is an error!");

        String correlation = getCorrelationId();
        new Thread(() -> doLog(Severity.ERROR, correlation, message, Optional.ofNullable(responseDto), Optional.ofNullable(e))).start();

    }

    public static void warning(String message) {

        responseDto.setReturnCode("01");
        responseDto.setReturnMessage("This is a warning!");

        String correlation = getCorrelationId();
        new Thread(() -> doLog(Severity.WARN, correlation, message, Optional.ofNullable(responseDto), Optional.empty())).start();

    }

    public static void info(String message) {

        responseDto.setReturnCode("00");
        responseDto.setReturnMessage("This is an info!");

        String correlation = getCorrelationId();
        new Thread(() -> doLog(Severity.INFO, correlation, message, Optional.ofNullable(responseDto), Optional.empty())).start();

    }

    public static void debug(String message) {

        responseDto.setReturnCode("03");
        responseDto.setReturnMessage("This is a debug!");

        String correlation = getCorrelationId();
        new Thread(() -> doLog(Severity.DEBUG, correlation, message, Optional.ofNullable(responseDto), Optional.empty())).start();

    }

    public static void trace(String message) {

        responseDto.setReturnCode("99");
        responseDto.setReturnMessage("This is a trace!");

        String correlation = getCorrelationId();
        new Thread(() -> doLog(Severity.TRACE, correlation, message, Optional.ofNullable(responseDto), Optional.empty())).start();

    }



    private static void doLog(Severity severity, String correlationId, String message, Optional<Object> payload, Optional<Throwable> exception){

        try {

            Map<String, Object> data = new HashMap<>();
            ObjectMapper mapper = new ObjectMapper();

            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];

            data.put("severity", severity.getValue());
            data.put("correlationId", correlationId);
            data.put("fqdn", InetAddress.getLocalHost().getHostName());
            data.put("service", serviceId);
            data.put("domain", domain);
            data.put("codePoint", stackTraceElement.getClassName().concat(" : ").concat(String.valueOf(stackTraceElement.getLineNumber())));
            data.put("message", message);

            payload.ifPresent(p -> data.put("payload", p));
            exception.ifPresent(e -> data.put("thrownException", ExceptionUtils.getStackTrace(e)));

            String log = mapper.writeValueAsString(data);

            switch (severity){

                case ERROR:
                    logger.error(log);
                    break;

                case WARN:
                    logger.warn(log);
                    break;

                case INFO:
                    logger.info(log);
                    break;

                case DEBUG:
                    logger.debug(log);
                    break;

                case TRACE:
                    logger.trace(log);
                    break;

            }

        } catch (JsonProcessingException | UnknownHostException e) {


        }

    }

}
