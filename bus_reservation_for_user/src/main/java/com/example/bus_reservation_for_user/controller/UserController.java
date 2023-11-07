package com.example.bus_reservation_for_user.controller;

import com.example.bus_reservation_for_user.apiresponse.ApiResponse;
import com.example.bus_reservation_for_user.dto.Commondto;
import com.example.bus_reservation_for_user.dto.LoginDto;
import com.example.bus_reservation_for_user.dto.SignUpDTo;
import com.example.bus_reservation_for_user.service.UserServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServiceClass userService;

    @PostMapping(value = "/signup")
    public ResponseEntity<ApiResponse> signUp(@Valid @RequestBody SignUpDTo signUpDto){
        return userService.signUp(signUpDto);
    }
    @PostMapping(value = "/login")
    public ApiResponse login(@RequestBody LoginDto loginDto){
        return userService.login(loginDto);
    }

    @GetMapping(value = "/get/availability")
    public ApiResponse getAvailability(){
        return userService.getAvailability();
    }
    @PostMapping(value = "/book/ticket")
    public ApiResponse bookTicket(@RequestBody Commondto commonDto) throws Exception {
        return userService.bookTicket(commonDto);
    }

    @PutMapping(value = "/delete/oneticket")
    public ApiResponse deleteOneTicket(@RequestBody Commondto commonDto){
        return userService.deleteOneTicket(commonDto);
    }
    @PutMapping(value = "/change/password")
    public ApiResponse changePassword(@RequestBody Commondto commonDto){
        return userService.changePassword(commonDto);
    }
    @PostMapping(value = "/forgot/password")
    public ApiResponse forgotPassword(@RequestBody Commondto commonDto){
        return userService.forgotPassword(commonDto);
    }
    @PutMapping(value = "/reset/password")
    public ApiResponse resetPassword(@RequestBody Commondto commonDto){
        return userService.resetPassword(commonDto);
    }
}
