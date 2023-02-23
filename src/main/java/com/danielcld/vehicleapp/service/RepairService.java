package com.danielcld.vehicleapp.service;

import com.danielcld.vehicleapp.model.Repair;
import com.danielcld.vehicleapp.repository.RepairRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RepairService {

    private RepairRepository repairRepository;


    public RepairService(RepairRepository repairRepository) {
        this.repairRepository = repairRepository;
    }

    public List<Repair> findRepairByVehicleId(int vehicleId) {
        return repairRepository.findRepairByVehicleId(vehicleId);
    }

    public List<Repair> getAllRepairs() {
        List<Repair> repairs = new ArrayList<>();
        repairRepository.findAll().forEach(repairs::add);
        return repairs;
    }

    public Repair saveRepair(Repair repair) {
        return repairRepository.save(repair);
    }
}
