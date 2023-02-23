package com.danielcld.vehicleapp.controller;

import com.danielcld.vehicleapp.model.User;
import com.danielcld.vehicleapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;


    @GetMapping(value = {"/", "/login"})
    public String login() {
        return "login";
    }


    @GetMapping(value = "/registration")
    public String registration(Model model) {

        User user = new User();
        model.addAttribute("user", user);

        return "registration";
    }


    @PostMapping(value = "/registration")
    public String createNewUser(@Valid User user, BindingResult bindingResult, Model model) {

        User userExists = userService.findUserByUserName(user.getUserName());
        if (userExists != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            "Istnieje juz uzytkownik z taka nazwa");
        }
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {

            userService.saveUser(user);
            model.addAttribute("successMessage", "Rejestracja zakonczona sukcesem");
            model.addAttribute("user", new User());


        }
        return "registration";
    }

}
