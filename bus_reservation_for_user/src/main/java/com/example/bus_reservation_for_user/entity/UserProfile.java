package com.example.bus_reservation_for_user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "userProfileTable")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "gender")
    private String gender;
    @Column(name = "age")
    private Long age;
    @Column(name = "phoneNumber")
    private Long phoneNumber;

    @Column(name = "ticketCount")
    private Long ticketCount;
    //    @Column(name = "name")
//    private String name;
    @Column(name = "ticketAmount")
    private Long ticketAmount;
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

}
