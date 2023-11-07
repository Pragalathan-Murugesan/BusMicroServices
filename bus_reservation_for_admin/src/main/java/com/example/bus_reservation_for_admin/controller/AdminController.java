package com.example.bus_reservation_for_admin.controller;

import com.example.bus_reservation_for_admin.api_response.ApiResponse;
import com.example.bus_reservation_for_admin.dto.Commondto;
import com.example.bus_reservation_for_admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @PostMapping(value = "/adduserprofile")
    public ApiResponse addUserProfile(@RequestBody Commondto commonDto){
        return adminService.addUserProfile(commonDto);
    }
    @PostMapping(value = "/adminlogin")
    public ApiResponse adminLogin(@RequestBody Commondto commonDto){
        return adminService.adminLogin(commonDto);
    }
    @DeleteMapping(value = "/delete/user/{id}")
    public ApiResponse deleteUser(@PathVariable Long id){
        return adminService.deleteUser(id);
    }
    @PutMapping(value = "/update/user")
    public ApiResponse updateUser(@RequestBody Commondto commonDto){
        return adminService.updateUser(commonDto);
    }

}
