package NeuroActivity.letstalk.controller;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("thinks")
public class ThinkController {

    private List<Map<String, String>> thinks = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First message"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second message"); }});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third message"); }});
    }};

    @GetMapping
    public List<Map<String, String>> getListOfThinks(){
        return thinks;
    }

    @GetMapping("{id}")
    public String getOneThink(@PathVariable int id){
        return thinks.get(id).get("text");
    }

    @PostMapping
    public String createThink(String think){
        return think + " created";
    }

    @PutMapping("{id}")
    public String changeThink(@PathVariable int id, String think){
        return "Think " + id + " changed";
    }

    @DeleteMapping("{id}")
    public String deleteThink(@PathVariable int id){
        return "Think " + id + " deleted";
    }
}
