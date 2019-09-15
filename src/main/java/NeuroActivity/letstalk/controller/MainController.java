package NeuroActivity.letstalk.controller;

import NeuroActivity.letstalk.domain.User;
import NeuroActivity.letstalk.domain.Views;
import NeuroActivity.letstalk.dto.ThinkPageDTO;
import NeuroActivity.letstalk.repository.UserRepo;
import NeuroActivity.letstalk.service.ThinkService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@RequestMapping("/")
@Controller
public class MainController {

    private final ThinkService thinkService;
    private final UserRepo userRepo;

    @Value("${spring.profiles.active}")
    private String profile;
    private final ObjectWriter thinkWriter;
    private final ObjectWriter profileWriter;

    @Autowired
    public MainController(ThinkService thinkService, UserRepo userRepo, ObjectMapper mapper) {
        this.thinkService = thinkService;
        this.userRepo = userRepo;

        this.thinkWriter = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(Views.FullPost.class);

        this.profileWriter = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(Views.FullProfile.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        HashMap<String, Object> frontData = new HashMap<>();

        if(user != null){
            user.setPassword("****");

            User userByDB = userRepo.findById(user.getId()).get();
            String serializedProfile = profileWriter.writeValueAsString(userByDB);
            model.addAttribute("profile", serializedProfile);

            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(0, ThinkController.THINK_PER_PAGE, sort);
            ThinkPageDTO thinkPageDTO = thinkService.findForUser(pageRequest, user);

            String thinks = thinkWriter.writeValueAsString(thinkPageDTO.getThinks());

            model.addAttribute("thinks", thinks);
            frontData.put("currentPage", thinkPageDTO.getCurrentPage());
            frontData.put("totalPages", thinkPageDTO.getTotalPage());

        }else{
            model.addAttribute("thinks", "[]");
            model.addAttribute("profile", "null");
        }


        model.addAttribute("frontData", frontData);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}