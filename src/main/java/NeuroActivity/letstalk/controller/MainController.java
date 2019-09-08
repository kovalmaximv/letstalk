package NeuroActivity.letstalk.controller;

import NeuroActivity.letstalk.domain.User;
import NeuroActivity.letstalk.domain.Views;
import NeuroActivity.letstalk.dto.ThinkPageDTO;
import NeuroActivity.letstalk.repository.ThinkRepo;
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

    @Value("${spring.profiles.active}")
    private String profile;
    private final ObjectWriter objectWriter;

    @Autowired
    public MainController(ThinkService thinkService, ObjectMapper mapper) {
        this.thinkService = thinkService;

        this.objectWriter = mapper
                .setConfig(mapper.getSerializationConfig())
                .writerWithView(Views.FullPost.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) throws JsonProcessingException {
        HashMap<String, Object> frontData = new HashMap<>();

        if(user != null){
            user.setPassword("****");
            frontData.put("profile", user);

            Sort sort = Sort.by(Sort.Direction.DESC, "id");
            PageRequest pageRequest = PageRequest.of(0, ThinkController.THINK_PER_PAGE, sort);
            ThinkPageDTO thinkPageDTO = thinkService.findAll(pageRequest);

            String thinks = objectWriter.writeValueAsString(thinkPageDTO.getThinks());

            model.addAttribute("thinks", thinks);
            frontData.put("currentPage", thinkPageDTO.getCurrentPage());
            frontData.put("tptalPages", thinkPageDTO.getTotalPage());

        }else{
            model.addAttribute("thinks", "[]");
        }


        model.addAttribute("frontData", frontData);
        model.addAttribute("isDevMode", "dev".equals(profile));

        return "index";
    }
}