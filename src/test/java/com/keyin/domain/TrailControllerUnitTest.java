package com.keyin.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrailControllerUnitTest {

    @Mock
    private TrailService trailService;

    @Test
    public void getTrailByIdReturnsOkWhenFound() {
        Trail t = new Trail();
        t.setId(10L);
        when(trailService.getTrailById(10L)).thenReturn(Optional.of(t));

        TrailController controller = new TrailController();
        ReflectionTestUtils.setField(controller, "trailService", trailService);

        ResponseEntity<Trail> resp = controller.getTrailById(10L);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(10L, resp.getBody().getId());
    }

    @Test
    public void getTrailByIdReturnsNotFoundWhenMissing() {
        when(trailService.getTrailById(11L)).thenReturn(Optional.empty());
        TrailController controller = new TrailController();
        ReflectionTestUtils.setField(controller, "trailService", trailService);

        ResponseEntity<Trail> resp = controller.getTrailById(11L);
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
    }

    @Test
    public void updateTrailReturnsOkWhenUpdated() {
        Trail updated = new Trail();
        updated.setId(20L);
        updated.setName("U");
        when(trailService.updateTrail(eq(20L), any(Trail.class))).thenReturn(Optional.of(updated));

        TrailController controller = new TrailController();
        ReflectionTestUtils.setField(controller, "trailService", trailService);

        ResponseEntity<Trail> resp = controller.updateTrail(20L, new Trail());
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(20L, resp.getBody().getId());
    }

    @Test
    public void updateTrailReturnsNotFoundWhenMissing() {
        when(trailService.updateTrail(eq(21L), any(Trail.class))).thenReturn(Optional.empty());
        TrailController controller = new TrailController();
        ReflectionTestUtils.setField(controller, "trailService", trailService);

        ResponseEntity<Trail> resp = controller.updateTrail(21L, new Trail());
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
    }

    @Test
    public void deleteTrailReturnsNoContentWhenDeleted() {
        when(trailService.deleteTrail(30L)).thenReturn(true);
        TrailController controller = new TrailController();
        ReflectionTestUtils.setField(controller, "trailService", trailService);

        ResponseEntity<Void> resp = controller.deleteTrail(30L);
        assertEquals(HttpStatus.NO_CONTENT, resp.getStatusCode());
    }

    @Test
    public void deleteTrailReturnsNotFoundWhenMissing() {
        when(trailService.deleteTrail(31L)).thenReturn(false);
        TrailController controller = new TrailController();
        ReflectionTestUtils.setField(controller, "trailService", trailService);

        ResponseEntity<Void> resp = controller.deleteTrail(31L);
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
    }

    @Test
    public void createTrailDelegatesAndReturns() {
        Trail created = new Trail();
        created.setId(40L);
        created.setName("C");
        when(trailService.createTrail(any(Trail.class))).thenReturn(created);

        TrailController controller = new TrailController();
        ReflectionTestUtils.setField(controller, "trailService", trailService);

        Trail resp = controller.createTrail(new Trail());
        assertNotNull(resp);
        assertEquals(40L, resp.getId());
        verify(trailService).createTrail(any(Trail.class));
    }
}

