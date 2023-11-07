package com.example.bus_reservation_for_user.exception;

import com.example.bus_reservation_for_user.apiresponse.ApiResponse;
import com.example.bus_reservation_for_user.exception.CustomizeException;
import com.example.bus_reservation_for_user.exception.IlllegalAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

@RestControllerAdvice
public class GlobalException {
    @Autowired
    private ApiResponse apiResponse;
    @ExceptionHandler
    public ApiResponse handleGlobalException(MethodArgumentNotValidException e){
        HashMap<String,Object> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach((error) ->{
            String fieldError = error.getField();
            String message=error.getDefaultMessage();
            errors.put(fieldError,message);
            apiResponse.setData(errors);
            apiResponse.setMessage("Something Went To Wrong");
            apiResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        });
        return apiResponse;
    }

    @ExceptionHandler(IlllegalAuthException.class)
    public ApiResponse handleIlllegalAuthException(IlllegalAuthException e){
        apiResponse.setMessage("UnAuthorized2 Access");
        apiResponse.setStatus(HttpStatus.UNAUTHORIZED);
        apiResponse.setData(null);
        return apiResponse;
    }
    @ExceptionHandler(CustomizeException.class)
    public ApiResponse handleCustomizeException(CustomizeException e){
        apiResponse.setMessage("Internal Server Error");
        apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        apiResponse.setData(null);
        return apiResponse;
    }
}
