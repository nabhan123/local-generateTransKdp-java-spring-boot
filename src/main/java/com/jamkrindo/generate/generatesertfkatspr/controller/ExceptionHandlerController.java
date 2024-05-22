package com.jamkrindo.generate.generatesertfkatspr.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> customHandleNotValid(Exception exs, WebRequest request, MethodArgumentNotValidException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        Map<String, Object> errors = new HashMap<>();

        errors.put("success", false);

        if(exs instanceof org.springframework.web.bind.MethodArgumentNotValidException) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = ((org.springframework.web.bind.MethodArgumentNotValidException) exs).getBindingResult().getFieldErrors();
            System.out.println(fieldErrors);
            for(FieldError fieldError: fieldErrors){
                sb.append(fieldError.getDefaultMessage()+ " pada object : "+fieldError.getField()+", ");
            }
            sb.toString().substring(sb.length() - 2);
            errors.put("message",sb.toString().substring(0, sb.length() - 2));
        }
        else{
            errors.put("message",exs.getLocalizedMessage());
        }

        System.out.println("request : "+request);

        errors.put("data", null);
        errors.put("responseCode", "01");

        return new ResponseEntity<>(errors, HttpStatus.OK);

    }
}
