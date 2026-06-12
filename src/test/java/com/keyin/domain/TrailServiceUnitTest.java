package com.keyin.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrailServiceUnitTest {

    @Mock
    private TrailRepository trailRepository;

    @InjectMocks
    private TrailService trailService;

    @Test
    public void createTrailDelegatesToRepository() {
        Trail t = new Trail();
        t.setName("T");
        when(trailRepository.save(any(Trail.class))).thenAnswer(i -> {
            Trail arg = i.getArgument(0);
            arg.setId(1L);
            return arg;
        });

        Trail saved = trailService.createTrail(t);
        assertNotNull(saved);
        assertEquals(1L, saved.getId());
        verify(trailRepository).save(t);
    }

    @Test
    public void getTrailByIdReturnsOptional() {
        Trail t = new Trail();
        t.setId(2L);
        when(trailRepository.findById(2L)).thenReturn(Optional.of(t));

        Optional<Trail> res = trailService.getTrailById(2L);
        assertTrue(res.isPresent());
        assertEquals(2L, res.get().getId());
    }

    @Test
    public void updateTrailWhenFound() {
        Trail existing = new Trail();
        existing.setId(3L);
        existing.setName("Old");

        Trail updated = new Trail();
        updated.setName("New");
        updated.setLength(5.0);
        updated.setLoop(true);
        updated.setStartLocation("S");
        updated.setEndLocation("E");

        when(trailRepository.findById(3L)).thenReturn(Optional.of(existing));
        when(trailRepository.save(any(Trail.class))).thenAnswer(i -> i.getArgument(0));

        Optional<Trail> res = trailService.updateTrail(3L, updated);
        assertTrue(res.isPresent());
        Trail r = res.get();
        assertEquals("New", r.getName());
        assertEquals(5.0, r.getLength());
        assertTrue(r.isLoop());
        assertEquals("S", r.getStartLocation());
        assertEquals("E", r.getEndLocation());

        verify(trailRepository).findById(3L);
        verify(trailRepository).save(existing);
    }

    @Test
    public void updateTrailWhenNotFound() {
        when(trailRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Trail> res = trailService.updateTrail(99L, new Trail());
        assertTrue(res.isEmpty());
        verify(trailRepository).findById(99L);
        verify(trailRepository, never()).save(any());
    }

    @Test
    public void deleteTrailReturnsTrueWhenDeleted() {
        when(trailRepository.existsById(4L)).thenReturn(true);

        boolean deleted = trailService.deleteTrail(4L);
        assertTrue(deleted);
        verify(trailRepository).deleteById(4L);
    }

    @Test
    public void deleteTrailReturnsFalseWhenMissing() {
        when(trailRepository.existsById(5L)).thenReturn(false);

        boolean deleted = trailService.deleteTrail(5L);
        assertFalse(deleted);
        verify(trailRepository, never()).deleteById(anyLong());
    }
}
