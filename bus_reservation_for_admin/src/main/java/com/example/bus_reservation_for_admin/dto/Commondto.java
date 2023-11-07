package com.example.bus_reservation_for_admin.dto;

import com.example.bus_reservation_for_admin.entity.UserProfile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Commondto {
    private Long id;
    private String name;
    private String emailID;
    private String password;
    private String role;
    private Long createAt;
    private UserProfile userProfile;
    private String gender;
    private Long age;
    private Long phoneNumber;
    private String ticketStatus;
    private Long ticketCount;
    private Long loginAt;
    private Long UpdateAt;
    private Long userId;
    private Long totalNumberOfTickets;
    private Long deleteTicket;
    private String newPassword;
    private String confirmPassword;
    private Long otpNumber;
}
