package com.danielcld.vehicleapp.repository;


import com.danielcld.vehicleapp.model.Reminder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReminderRepository extends CrudRepository<Reminder, Integer> {

    Reminder findReminderByVehicleId(int vehicleID);
}
