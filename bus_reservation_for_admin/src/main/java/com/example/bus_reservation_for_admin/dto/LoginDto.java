package com.example.bus_reservation_for_admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginDto {
    @NotNull(message = "role is Could Not Be Null")
    private String role;
    @NotNull(message = "emailID is Could not Be Null")
    private String emailID;;
}
