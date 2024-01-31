package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    AccidentService accidentService;

    @Test
    @WithMockUser
    public void shouldReturnCreateAccidentMessage() throws Exception {
        var expectedTypes = List.of(
                new AccidentType(1, "Две машины"),
                new AccidentType(2, "Машина и человек"),
                new AccidentType(3, "Машина и велосипед")
        );
        var expectedRules = List.of(
                new Rule(1, "Статья. 1"),
                new Rule(2, "Статья. 2"),
                new Rule(3, "Статья. 3")
        );
        this.mockMvc.perform(get("/accidents/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/createAccident"))
                .andExpect(model().attribute("rules", expectedRules))
                .andExpect(model().attribute("types", expectedTypes));
    }

    @Test
    @WithMockUser
    public void shouldReturnEditUpdateAccidentMessage() throws Exception {
        int id = accidentService.findAll().stream().findFirst().get().getId();
        this.mockMvc.perform(get("/accidents/formUpdateAccident").param("id", String.valueOf(id)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("accidents/editAccident"));
    }

    @Test
    @WithMockUser
    public void shouldReturnErrorMessageWhenSearchWithIncorrectId() throws Exception {
        int id = -1;
        this.mockMvc.perform(get("/accidents/formUpdateAccident").param("id", String.valueOf(id)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"))
                .andExpect(model().attribute("message", "Инцидент не найден"));
    }

}