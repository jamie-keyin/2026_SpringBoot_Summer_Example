package com.keyin.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Trail> getTrailById(@PathVariable Long id) {
        return trailService.getTrailById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/trail/{id}")
    public ResponseEntity<Trail> updateTrail(@PathVariable Long id, @RequestBody Trail trail) {
        return trailService.updateTrail(id, trail)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/trail/{id}")
    public ResponseEntity<Void> deleteTrail(@PathVariable Long id) {
        boolean deleted = trailService.deleteTrail(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
