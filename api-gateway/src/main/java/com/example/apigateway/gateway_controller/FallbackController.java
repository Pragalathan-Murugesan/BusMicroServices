package com.example.apigateway.gateway_controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @RequestMapping("/fallback-service1")
    public String fallbackService1() {
        return "Fallback response for service 1";
    }

    @RequestMapping("/fallback-service2")
    public String fallbackService2() {
        return "Fallback response for service 2";
    }
}