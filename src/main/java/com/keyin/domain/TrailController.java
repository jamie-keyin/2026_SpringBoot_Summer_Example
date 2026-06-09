package com.keyin.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class TrailController {
    @Autowired
    private TrailService trailService;

    @PostMapping("/trail")
    public Trail createTrail(@RequestBody Trail trail) {
        return trailService.createTrail(trail);
    }

    @GetMapping("/trail/{id}")
    public Trail getTrailById(@PathVariable Long id) {
        return trailService.getTrailById(id);
    }
}
