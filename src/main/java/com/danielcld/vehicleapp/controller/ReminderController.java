package com.danielcld.vehicleapp.controller;


import com.danielcld.vehicleapp.model.Reminder;
import com.danielcld.vehicleapp.model.Vehicle;
import com.danielcld.vehicleapp.service.ReminderService;
import com.danielcld.vehicleapp.service.UserService;
import com.danielcld.vehicleapp.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class ReminderController {

    @Autowired
    private UserService userService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private ReminderService reminderService;

    @GetMapping("/vehicle/{vehicleId}/reminder")
    public String showEditForm(@PathVariable int vehicleId, Vehicle vehicle, Model model) {

        Reminder reminder = reminderService.findReminderByVehicleId(vehicleId);
        model.addAttribute("reminder", reminder);

        return "add-reminder";
    }

    @PostMapping("/vehicle/{vehicleId}/addreminder")
    public String EditVehicle(Reminder reminder, @PathVariable int vehicleId) {

        reminder.setId(reminderService.findReminderByVehicleId(vehicleId).getId());
        reminder.setVehicle(vehicleService.findOne(vehicleId));
        reminder = reminderService.checkNull(reminder);

        reminderService.saveReminder(reminder);

        return "redirect:/vehicle/{vehicleId}";
    }

}
