package NeuroActivity.letstalk.controller;


import NeuroActivity.letstalk.domain.Think;
import NeuroActivity.letstalk.domain.User;
import NeuroActivity.letstalk.domain.Views;
import NeuroActivity.letstalk.dto.ThinkPageDTO;
import NeuroActivity.letstalk.service.ThinkService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/thinks")
public class ThinkController {
    public static final int THINK_PER_PAGE = 3;

    private final ThinkService thinkService;

    @Autowired
    public ThinkController(ThinkService thinkService) {
        this.thinkService = thinkService;
    }

    @GetMapping
    @JsonView(Views.FullPost.class)
    public ThinkPageDTO getListOfThinks(
            @PageableDefault(size = THINK_PER_PAGE, sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ){
        return thinkService.findAll(pageable);
    }

    @GetMapping("{id}")
    @JsonView(Views.FullPost.class)
    public Think getOneThink(@PathVariable("id") Think think){
        return think;
    }

    @PostMapping
    public Think createThink(
            @RequestBody Think think,
            @AuthenticationPrincipal User user
    ) throws IOException {
        return thinkService.create(think, user);
    }

    @PutMapping("{id}")
    public Think changeThink(@PathVariable("id") Think thinkDatabase, @RequestBody Think thinkUser) throws IOException {
        return thinkService.update(thinkDatabase, thinkUser);
    }

    @DeleteMapping("{id}")
    public void deleteThink(@PathVariable("id") Think think){
        thinkService.delete(think);
    }


}