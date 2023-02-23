package com.danielcld.vehicleapp.repository;

import com.danielcld.vehicleapp.model.Repair;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairRepository extends CrudRepository<Repair, Integer> {
    List<Repair> findRepairByVehicleId(int vehicleID);
}
