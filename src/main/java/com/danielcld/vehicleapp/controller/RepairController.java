package com.danielcld.vehicleapp.controller;


import com.danielcld.vehicleapp.model.Repair;
import com.danielcld.vehicleapp.model.User;
import com.danielcld.vehicleapp.model.Vehicle;
import com.danielcld.vehicleapp.service.RepairService;
import com.danielcld.vehicleapp.service.UserService;
import com.danielcld.vehicleapp.service.VehicleService;
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
public class RepairController {

    @Autowired
    private UserService userService;
    @Autowired
    private VehicleService vehicleService;
    @Autowired
    private RepairService repairService;


    @GetMapping("/vehicle/{vehicleId}/repair")
    public String showRepairForm(@PathVariable int vehicleId, Vehicle vehicle, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        vehicle = vehicleService.findOne(vehicleId);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute(new Repair());
        return "add-repair";
    }

    @PostMapping("/vehicle/{vehicleId}/addrepair")
    public String addRepair(@ModelAttribute @Valid Repair repair, BindingResult result, Model model, Vehicle vehicle, @PathVariable int vehicleId) {
        if (result.hasErrors()) {
            return "add-repair";
        }
        vehicle = vehicleService.findOne(vehicleId);

        repair.setVehicle(vehicle);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByUserName(auth.getName());
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("repair", repair);

        repairService.saveRepair(repair);
        return "redirect:/vehicle/{vehicleId}";
    }

}
