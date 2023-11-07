package com.example.bus_reservation_for_admin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "userTable")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "emailID")
    private String emailID;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "createAt")
    private Long createAt;
    @Column(name = "loginAt")
    private Long loginAt;
    @Column(name = "updateAt")
    private Long updateAt;
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private UserProfile userProfile;
    @Column(name = "otpNumber")
    private Long otpNumber;

}
