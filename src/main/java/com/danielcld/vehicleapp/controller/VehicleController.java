package com.danielcld.vehicleapp.controller;

import com.danielcld.vehicleapp.model.Reminder;
import com.danielcld.vehicleapp.model.User;
import com.danielcld.vehicleapp.model.Vehicle;
import com.danielcld.vehicleapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class VehicleController {

    @Autowired
    private UserService userService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private RepairService repairService;
    @Autowired
    private ReminderService reminderService;


    @GetMapping("/vehicles")
    public String showVehicles(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        model.addAttribute("vehicles",vehicleService.findVehicleByUserId(user.getId()));

        return "vehicles";
    }


    @GetMapping("/vehicle/{vehicleId}")
    public String vehicle(@PathVariable int vehicleId, Vehicle vehicle, Model model){

        vehicle = vehicleService.findOne(vehicleId);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("repairs", repairService.findRepairByVehicleId(vehicleId));

        return "/vehicle";
    }

    @GetMapping("/addvehicle")
    public String showVehicleForm(Vehicle vehicle)
    {
        return "add-vehicle";
    }

    @PostMapping("/addvehicle")
    public String addVehicle(@Valid Vehicle vehicle, BindingResult result, Model model) throws Exception {
        if (result.hasErrors()) {
            return "add-vehicle";
        }

        ScrappingService scrappingService = new ScrappingService();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        vehicle.setUser(user);
        scrappingService.downloadVehicleDetails(vehicle);

        vehicleService.saveVehicle(vehicle);
        Reminder reminder = new Reminder(1,false,false,false,vehicle);
        reminderService.saveReminder(reminder);
        vehicle.setReminder(new Reminder(1,false,false,false,vehicle));



        return "redirect:/vehicles";
    }

    @GetMapping("/vehicle/{vehicleId}/delete")
    public String deleteVehicle(@PathVariable int vehicleId)
    {
        vehicleService.deleteVehicle(vehicleId);

        return "redirect:/vehicles";
    }


    @GetMapping("/vehicle/{vehicleId}/editvehicle")
    public String showEditForm(@PathVariable int vehicleId, Vehicle vehicle, Model model) {

        model.addAttribute("vehicle", vehicleService.findOne(vehicleId));

        return "edit-vehicle";
    }

    @PostMapping("/vehicle/{vehicleId}/addedit")
    public String EditVehicle(@ModelAttribute @Valid Vehicle vehicle, BindingResult result, Model model, @PathVariable int vehicleId) {
        if (result.hasErrors()) {
            return "edit-vehicle";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());

        vehicle.setId(vehicleService.findOne(vehicleId).getId());
        vehicle.setUser(user);

        vehicleService.saveVehicle(vehicle);
        return "redirect:/vehicle/{vehicleId}";
    }

}
