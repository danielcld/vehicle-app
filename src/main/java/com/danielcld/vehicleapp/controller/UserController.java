package com.danielcld.vehicleapp.controller;


import com.danielcld.vehicleapp.model.User;
import com.danielcld.vehicleapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping("/user/edituser")
    public String showEditForm(User user, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        user = userService.findUserByUserName(auth.getName());
        model.addAttribute("user", user);

        return "edit-user";
    }

    @PostMapping("/user/edit")
    public String editUser(@Valid User user, BindingResult bindingResult) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        user.setId(userService.findUserByUserName(auth.getName()).getId());
        user.setPassword(userService.findUserByUserName(auth.getName()).getPassword());
        user.setUserName(userService.findUserByUserName(auth.getName()).getUserName());
        user.setRoles(userService.findUserByUserName(auth.getName()).getRoles());
        userService.editUser(user);

        return "redirect:/vehicles";
    }


    @GetMapping("/admin/users")
    public String manageUsers(Model model) {

        model.addAttribute("users", userService.getAllUsers());

        return "admin/users";
    }

    @GetMapping("/admin/deleteuser/{userId}")
    public String deleteUser(@PathVariable int userId) {

        userService.deleteUser(userId);

        return "redirect:/admin/users";
    }


}
