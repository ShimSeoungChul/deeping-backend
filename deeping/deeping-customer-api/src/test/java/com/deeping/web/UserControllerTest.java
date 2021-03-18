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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    public void create() throws Exception {
        User mockUsers = User.builder()
                .id(1004L)
                .phone("010-0000-0000")
                .name("Tester")
                .password("test")
                .build();;

        given(userService.registerUser("010-0000-0000","Tester","test"))
                .willReturn(mockUsers);

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"phone\":\"010-0000-0000\",\"name\":\"Tester\",\"password\":\"test\"}"))
                .andExpect(status().isCreated());

        verify(userService).registerUser(
                eq("010-0000-0000"), eq("Tester"), eq("test"));
    }
}