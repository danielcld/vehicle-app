package com.danielcld.vehicleapp.repository;

import com.danielcld.vehicleapp.model.Vehicle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends CrudRepository<Vehicle, Integer> {

    List<Vehicle> findVehicleByUser_Id(int userID);
    Vehicle findVehicleById(int vehicleId);

}
