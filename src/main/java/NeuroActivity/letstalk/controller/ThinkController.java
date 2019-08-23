package NeuroActivity.letstalk.controller;


import NeuroActivity.letstalk.domain.Think;
import NeuroActivity.letstalk.domain.Views;
import NeuroActivity.letstalk.dto.EventType;
import NeuroActivity.letstalk.dto.ObjectType;
import NeuroActivity.letstalk.repository.ThinkRepo;
import NeuroActivity.letstalk.util.WebSocketSender;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.EventListener;
import java.util.List;
import java.util.function.BiConsumer;

@RestController
@RequestMapping("/thinks")
public class ThinkController {

    private final ThinkRepo thinkRepo;
    private final BiConsumer<EventType, Think> webSocketSender;

    @Autowired
    public ThinkController(ThinkRepo thinkRepo, WebSocketSender webSocketSender) {
        this.thinkRepo = thinkRepo;
        this.webSocketSender = webSocketSender.getSender(ObjectType.THINK, Views.IdText.class);
    }

    @GetMapping
    @JsonView(Views.IdText.class)
    public List<Think> getListOfThinks(){
        return thinkRepo.findAll();
    }

    @GetMapping("{id}")
    @JsonView(Views.FullPost.class)
    public Think getOneThink(@PathVariable("id") Think think){
        return think;
    }

    @PostMapping
    public Think createThink(@RequestBody Think think){
        think.setDate(LocalDateTime.now());
        Think updatedThink = thinkRepo.save(think);

        webSocketSender.accept(EventType.UPDATE, updatedThink);
        return updatedThink;
    }

    @PutMapping("{id}")
    public Think changeThink(@PathVariable("id") Think thinkDatabase, @RequestBody Think thinkUser){
        BeanUtils.copyProperties(thinkUser, thinkDatabase, "id");

        Think updatedThink = thinkRepo.save(thinkDatabase);
        webSocketSender.accept(EventType.UPDATE, updatedThink);
        return updatedThink;
    }

    @DeleteMapping("{id}")
    public void deleteThink(@PathVariable("id") Think think){
        thinkRepo.delete(think);
        webSocketSender.accept(EventType.REMOVE, think);
    }
}
