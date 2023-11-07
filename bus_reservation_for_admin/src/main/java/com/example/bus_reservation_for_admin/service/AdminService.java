package com.example.bus_reservation_for_admin.service;


import com.example.bus_reservation_for_admin.api_response.ApiResponse;
import com.example.bus_reservation_for_admin.dto.Commondto;

public interface AdminService {
    ApiResponse addUserProfile(Commondto commonDto);

    ApiResponse deleteUser(Long id);

    ApiResponse adminLogin(Commondto commonDto);

    ApiResponse updateUser(Commondto commonDto);
}
