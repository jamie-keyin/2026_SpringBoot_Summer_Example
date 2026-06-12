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

    public java.util.Optional<Trail> getTrailById(Long id) {
        return trailRepository.findById(id);
    }

    public java.util.Optional<Trail> updateTrail(Long id, Trail updatedTrail) {
        return trailRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedTrail.getName());
                    existing.setLength(updatedTrail.getLength());
                    existing.setLoop(updatedTrail.isLoop());
                    existing.setStartLocation(updatedTrail.getStartLocation());
                    existing.setEndLocation(updatedTrail.getEndLocation());
                    return trailRepository.save(existing);
                });
    }

    public boolean deleteTrail(Long id) {
        if (trailRepository.existsById(id)) {
            trailRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
