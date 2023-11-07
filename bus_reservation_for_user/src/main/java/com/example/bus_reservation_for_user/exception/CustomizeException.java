package com.example.bus_reservation_for_user.exception;

public class CustomizeException extends RuntimeException{
    private String message;
    private Object status;
    private Object data;
    public CustomizeException(String message , Object status,Object data){
        this.data = data;
        this.message = message;
        this.status = status;
    }
}
