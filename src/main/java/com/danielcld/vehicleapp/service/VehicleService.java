package com.danielcld.vehicleapp.service;

import com.danielcld.vehicleapp.model.Vehicle;
import com.danielcld.vehicleapp.repository.UserRepository;
import com.danielcld.vehicleapp.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class VehicleService {

    private ReminderService reminderService;
    private UserRepository userRepository;
    private VehicleRepository vehicleRepository;

    public VehicleService(UserRepository userRepository, VehicleRepository vehicleRepository) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> findVehicleByUserId(int userID) {
        return vehicleRepository.findVehicleByUser_Id(userID);
    }

    public Vehicle findOne(int vehicleId) {
        return vehicleRepository.findVehicleById(vehicleId);
    }

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        vehicleRepository.findAll().forEach(vehicles::add);
        return vehicles;
    }

    public void saveVehicle(Vehicle vehicle) {
        vehicleRepository.save(vehicle);

    }

    public void deleteVehicle(int vehicleId) {
        vehicleRepository.deleteById(vehicleId);
    }

}
