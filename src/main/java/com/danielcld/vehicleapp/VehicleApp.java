package com.danielcld.vehicleapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class VehicleApp {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(VehicleApp.class, args);
    }
}
