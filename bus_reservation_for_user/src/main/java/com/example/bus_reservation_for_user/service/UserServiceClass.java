package com.example.bus_reservation_for_user.service;

import com.example.bus_reservation_for_user.apiresponse.ApiResponse;
import com.example.bus_reservation_for_user.dto.Commondto;
import com.example.bus_reservation_for_user.dto.LoginDto;
import com.example.bus_reservation_for_user.dto.SignUpDTo;
import org.springframework.http.ResponseEntity;

public interface UserServiceClass {
    ResponseEntity<ApiResponse> signUp(SignUpDTo signUpDto);

    ApiResponse login(LoginDto loginDto);

    ApiResponse bookTicket(Commondto commonDto) throws Exception;

    ApiResponse getAvailability();

    ApiResponse deleteOneTicket(Commondto commonDto);

    ApiResponse changePassword(Commondto commonDto);

    ApiResponse forgotPassword(Commondto commonDto);

    ApiResponse resetPassword(Commondto commonDto);
}
