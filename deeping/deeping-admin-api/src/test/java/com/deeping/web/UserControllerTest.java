package com.deeping.web;

import com.deeping.domain.User;
import com.deeping.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void list() throws Exception {
        List<User> users = new ArrayList<>();
        users.add(User.builder()
                .phone("010-7756-1989")
                .name("테스터")
                .level(1L)
                .build());

        given(userService.getUsers()).willReturn(users);
        mvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    //사용자 추가. 회원 가입 x.
    @Test
    public void create() throws Exception {
        String phone = "010-0000-0000";
        String name = "Administrator";

        User user = User.builder().phone(phone).name(name).build();

        given(userService.addUser(phone, name)).willReturn(user);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"phone\":\"010-0000-0000\",\"name\":\"Administrator\"}"))
                .andExpect(status().isCreated());

        verify(userService).addUser(phone,name);

    }

    @Test
    public void update() throws Exception {
        Long id = 1004L;
        String phone = "010-0000-0000";
        String name = "Administrator";
        Long level = 100L;

        User user = User.builder()
                .phone(phone)
                .name(name)
                .level(level)
                .build();

        given(userService.updateUser(id,phone, name,level)).willReturn(user);


        mvc.perform(patch("/users/1004")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"phone\":\"010-0000-0000\"," +
                        "\"name\":\"Administrator\",\"level\":100}"))
                .andExpect(status().isOk());

        verify(userService).updateUser(eq(id),eq(phone),eq(name),eq(level));
    }

    @Test
    public void deactivate() throws Exception{
        mvc.perform(delete("/users/1004"))
                .andExpect(status().isOk());

        verify(userService).deactiveUser(1004L);
    }

}