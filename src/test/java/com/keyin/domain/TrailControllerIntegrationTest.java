package com.keyin.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:testdb",
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.datasource.username=sa",
                "spring.datasource.password=",
                "spring.jpa.hibernate.ddl-auto=create-drop"
        })
public class TrailControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private RestTemplate restTemplate = new RestTemplate();

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void testUpdateTrail() {
        // Create a trail
        Trail t = new Trail();
        t.setName("Original");
        t.setLength(1.2);
        t.setLoop(false);
        t.setStartLocation("A");
        t.setEndLocation("B");

        ResponseEntity<Trail> createResp = restTemplate.postForEntity(baseUrl() + "/trail", t, Trail.class);
        assertEquals(HttpStatus.OK, createResp.getStatusCode());
        assertNotNull(createResp.getBody());
        Long id = createResp.getBody().getId();
        assertNotNull(id);

        // Update the trail
        Trail updated = new Trail();
        updated.setName("Updated");
        updated.setLength(2.5);
        updated.setLoop(true);
        updated.setStartLocation("X");
        updated.setEndLocation("Y");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Trail> entity = new HttpEntity<>(updated, headers);

        ResponseEntity<Trail> updateResp = restTemplate.exchange(baseUrl() + "/trail/" + id, HttpMethod.PUT, entity, Trail.class);
        assertEquals(HttpStatus.OK, updateResp.getStatusCode());
        assertNotNull(updateResp.getBody());
        assertEquals("Updated", updateResp.getBody().getName());
        assertEquals(2.5, updateResp.getBody().getLength());
        assertTrue(updateResp.getBody().isLoop());
        assertEquals("X", updateResp.getBody().getStartLocation());
        assertEquals("Y", updateResp.getBody().getEndLocation());
    }

    @Test
    public void testDeleteTrail() {
        // Create a trail
        Trail t = new Trail();
        t.setName("ToDelete");
        t.setLength(3.3);
        t.setLoop(false);
        t.setStartLocation("Start");
        t.setEndLocation("End");

        ResponseEntity<Trail> createResp = restTemplate.postForEntity(baseUrl() + "/trail", t, Trail.class);
        assertEquals(HttpStatus.OK, createResp.getStatusCode());
        Long id = createResp.getBody().getId();
        assertNotNull(id);

        // Delete
        ResponseEntity<Void> deleteResp = restTemplate.exchange(baseUrl() + "/trail/" + id, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, deleteResp.getStatusCode());

        // Ensure not found
        try {
            restTemplate.getForEntity(baseUrl() + "/trail/" + id, Trail.class);
            fail("Expected 404 Not Found");
        } catch (org.springframework.web.client.HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    @Test
    public void testCreateTrailIntegration() {
        Trail t = new Trail();
        t.setName("Created");
        t.setLength(4.4);
        t.setLoop(false);
        t.setStartLocation("S");
        t.setEndLocation("E");

        ResponseEntity<Trail> createResp = restTemplate.postForEntity(baseUrl() + "/trail", t, Trail.class);
        assertEquals(HttpStatus.OK, createResp.getStatusCode());
        assertNotNull(createResp.getBody());
        assertNotNull(createResp.getBody().getId());
        assertEquals("Created", createResp.getBody().getName());
    }

    @Test
    public void testGetTrailIntegration() {
        Trail t = new Trail();
        t.setName("ToGet");
        t.setLength(7.7);
        t.setLoop(true);
        t.setStartLocation("Start");
        t.setEndLocation("End");

        ResponseEntity<Trail> createResp = restTemplate.postForEntity(baseUrl() + "/trail", t, Trail.class);
        Long id = createResp.getBody().getId();

        ResponseEntity<Trail> getResp = restTemplate.getForEntity(baseUrl() + "/trail/" + id, Trail.class);
        assertEquals(HttpStatus.OK, getResp.getStatusCode());
        assertNotNull(getResp.getBody());
        assertEquals("ToGet", getResp.getBody().getName());
    }
}
