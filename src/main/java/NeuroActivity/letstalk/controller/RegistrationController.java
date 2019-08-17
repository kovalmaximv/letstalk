package NeuroActivity.letstalk.controller;

import NeuroActivity.letstalk.domain.User;
import NeuroActivity.letstalk.domain.UsersRoles;
import NeuroActivity.letstalk.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    UserRepo userRepo;

    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user){

        user.setLastVisit(LocalDateTime.now());
        user.setRoles(Collections.singleton(UsersRoles.User));
        userRepo.save(user);

        return "redirect:/login";
    }

}
