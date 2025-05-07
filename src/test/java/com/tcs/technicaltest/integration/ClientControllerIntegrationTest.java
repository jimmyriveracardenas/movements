package com.tcs.technicaltest.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.technicaltest.model.Person;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
  properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop"
  }
)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@Transactional
public class ClientControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    private Long savedPersonId;

    @BeforeEach
    public void setUp() {
        // Crear y persistir una persona para asociar al cliente
        Person person = Person.builder()
                .name("Integration Test User")
                .gender("O")
                .age(25)
                .identification("INTG12345")
                .address("Test Address 123")
                .phone("0999000112")
                .build();
        entityManager.persist(person);
        entityManager.flush();
        savedPersonId = person.getPersonId();
    }

    @Test
    public void createClient_returnsCreatedClient() throws Exception {
        // Preparar payload JSON para crear un cliente
        var payload = objectMapper.createObjectNode();
        var personNode = objectMapper.createObjectNode();
        personNode.put("personId", savedPersonId);
        payload.set("person", personNode);
        payload.put("password", "intgPass");
        payload.put("status", true);

        mockMvc.perform(post("/api/clients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payload)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.clientId").exists())
            .andExpect(jsonPath("$.person.personId").value(savedPersonId))
            .andExpect(jsonPath("$.password").value("intgPass"))
            .andExpect(jsonPath("$.status").value(true));
    }
}