package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.UserService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class PostControlTest {

    @MockBean
    private AccidentService accidentService;
    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void whenSaveThenReturnAccident() throws Exception {
        var type = new AccidentType(1, "Две машины");
        Accident accident = new Accident(0, "Accident1", "text1", "address1", type, new HashSet<>());
        when(accidentService.update(any(Accident.class), anySet())).thenReturn(true);
        this.mockMvc.perform(post("/accidents/saveAccident")
                        .param("name", accident.getName())
                        .param("text", accident.getText())
                        .param("address", accident.getAddress())
                        .param("type.id", String.valueOf(accident.getType().getId()))
                        .param("rIds", "1, 2, 3"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        ArgumentCaptor<Accident> accidentArgumentCaptor = ArgumentCaptor.forClass(Accident.class);
        ArgumentCaptor<Set<Integer>> setArgumentCaptor = ArgumentCaptor.forClass(Set.class);
        verify(accidentService).update(accidentArgumentCaptor.capture(), setArgumentCaptor.capture());

        assertThat(accidentArgumentCaptor.getValue().getName()).isEqualTo(accident.getName());
        assertThat(accidentArgumentCaptor.getValue().getText()).isEqualTo(accident.getText());
        assertThat(accidentArgumentCaptor.getValue().getAddress()).isEqualTo(accident.getAddress());
        assertThat(accidentArgumentCaptor.getValue().getType()).isEqualTo(accident.getType());
        assertThat(setArgumentCaptor.getValue().toString()).isEqualTo("[1, 2, 3]");

    }

    @Test
    @WithMockUser
    public void whenSaveThenReturnErrorMessage() throws Exception {
        var type = new AccidentType(1, "Две машины");
        Accident accident = new Accident(0, "Accident1", "text1", "address1", type, new HashSet<>());
        this.mockMvc.perform(post("/accidents/saveAccident")
                        .param("name", accident.getName())
                        .param("text", accident.getText())
                        .param("address", accident.getAddress())
                        .param("type.id", String.valueOf(accident.getType().getId()))
                        .param("rIds", "1, 2, 3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"));
    }

    @Test
    @WithMockUser
    public void whenRegisterNewUserThenGetOkMessage() throws Exception {
        User user = new User();
        user.setUserName("name");
        user.setPassword("pass");
        when(userService.add(any(User.class))).thenReturn(Optional.of(user));
        this.mockMvc.perform(post("/reg")
                        .param("userName", user.getUserName())
                        .param("password", user.getPassword()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).add(userCaptor.capture());

        assertThat(userCaptor.getValue().getUserName()).isEqualTo(user.getUserName());
        assertThat(userCaptor.getValue().getPassword()).isEqualTo(user.getPassword());

    }

    @Test
    @WithMockUser
    public void whenRegisterNewUserThenGetErrorMessage() throws Exception {
        this.mockMvc.perform(post("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("errors/404"))
                .andExpect(model().attribute("message", "Пользователь с таким логином уже существует"));
    }

}