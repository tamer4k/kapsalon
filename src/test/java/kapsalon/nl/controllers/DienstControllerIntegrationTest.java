package kapsalon.nl.controllers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class DienstControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Test
    void createDienst() throws Exception {
        // Arrange
        String requestJson = """
    {
        "category": "Men",
        "title": "Haar Knippen en stylen",
        "description": "Onderhoud van mannelijke kippen",
        "duration": 15,
        "price": 20
    }
    """;
        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/diensten")
                        .contentType(APPLICATION_JSON)
                        .content(requestJson))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("Men"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Haar Knippen en stylen"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Onderhoud van mannelijke kippen"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.duration").value(15.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(20.0))
        // Extra Assert
            .andExpect(result -> {
            String content = result.getResponse().getContentAsString();
            assertThat(content, containsString("Haar Knippen en stylen"));
        });
    }

    @Test
    void shouldGetDienstById() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/diensten/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category").value("Men"))
                .andExpect(jsonPath("$.title").value("Herenservice Deluxe"))
                .andExpect(jsonPath("$.description").value("Een uitgebreide onderhoudsservice voor mannen"))
                .andExpect(jsonPath("$.duration").value(30.0))
                .andExpect(jsonPath("$.price").value(30.0))
                .andReturn();
        // Extra Assert
        String responseContent = result.getResponse().getContentAsString();
        assertTrue(responseContent.contains("Herenservice Deluxe"));
    }
    @Test
    void shouldReturnNotFoundWhenDienstNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/diensten/{id}", 999))
                .andExpect(status().isNotFound());
    }
}