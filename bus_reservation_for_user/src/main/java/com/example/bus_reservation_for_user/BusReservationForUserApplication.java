package com.example.bus_reservation_for_user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BusReservationForUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusReservationForUserApplication.class, args);
	}

}
