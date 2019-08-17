package NeuroActivity.letstalk.controller;

import NeuroActivity.letstalk.domain.User;
import NeuroActivity.letstalk.repository.ThinkRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@RequestMapping("/")
@Controller
public class MainController {

    private final ThinkRepo thinkRepo;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    public MainController(ThinkRepo thinkRepo) {
        this.thinkRepo = thinkRepo;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user){
        HashMap<String, Object> frontData = new HashMap<>();
        if(user != null) user.setPassword("****");
        frontData.put("profile", user);
        frontData.put("thinks", thinkRepo.findAll());

        model.addAttribute("frontData", frontData);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}
