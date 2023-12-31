package com.example.bus_reservation_for_admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BusReservationForAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusReservationForAdminApplication.class, args);
	}

}
