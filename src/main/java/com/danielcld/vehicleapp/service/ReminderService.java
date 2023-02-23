package com.danielcld.vehicleapp.service;


import com.danielcld.vehicleapp.model.Reminder;
import com.danielcld.vehicleapp.repository.ReminderRepository;
import org.springframework.stereotype.Service;

@Service
public class ReminderService {

    private ReminderRepository reminderRepository;

    public ReminderService(ReminderRepository reminderRepository) {
        this.reminderRepository = reminderRepository;
    }

    public Reminder checkNull(Reminder reminder) {

        if (reminder.getActive() == null) {
            reminder.setActive(false);
        }
        if (reminder.getInspection() == null) {
            reminder.setInspection(false);
        }
        if (reminder.getInsurance() == null) {
            reminder.setInsurance(false);
        }
        return reminder;
    }

    public Reminder saveReminder(Reminder reminder) {
        return reminderRepository.save(reminder);
    }

    public Reminder findReminderByVehicleId(int vehicleID) {
        return reminderRepository.findReminderByVehicleId(vehicleID);
    }
}
