package com.keyin.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrailService {
    @Autowired
    private TrailRepository trailRepository;

    public Trail createTrail(Trail trail) {
        return trailRepository.save(trail);
    }

    public Trail getTrailById(Long id) {
        return trailRepository.findById(id).orElse(null);
    }
}
