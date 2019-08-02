package NeuroActivity.letstalk.controller;


import NeuroActivity.letstalk.domain.Think;
import NeuroActivity.letstalk.repository.ThinkRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("thinks")
public class ThinkController {

    private final ThinkRepo thinkRepo;

    @Autowired
    public ThinkController(ThinkRepo thinkRepo) {
        this.thinkRepo = thinkRepo;
    }

    @GetMapping
    public List<Think> getListOfThinks(){
        return thinkRepo.findAll();
    }

    @GetMapping("{id}")
    public Think getOneThink(@PathVariable("id") Think think){
        return think;
    }

    @PostMapping
    public Think createThink(@RequestBody Think think){
        think.setDate(LocalDateTime.now());
        return thinkRepo.save(think);
    }

    @PutMapping("{id}")
    public Think changeThink(@PathVariable("id") Think thinkDatabase, @RequestBody Think thinkUser){
        BeanUtils.copyProperties(thinkUser, thinkDatabase, "id");

        return thinkRepo.save(thinkDatabase);
    }

    @DeleteMapping("{id}")
    public void deleteThink(@PathVariable("id") Think think){
        thinkRepo.delete(think);
    }
}
