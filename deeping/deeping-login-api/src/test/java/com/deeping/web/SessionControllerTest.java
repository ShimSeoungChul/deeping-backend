package com.deeping.web;

import com.deeping.domain.User;
import com.deeping.service.PhoneNotExistedException;
import com.deeping.service.UserService;
import com.deeping.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(SessionController.class)
class SessionControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    JwtUtil jwtUtil;

    @MockBean
    private UserService userService;

    @Test
    public void createValidAttributes() throws Exception {

        Long id = 1004L;
        String phone = "010-0000-0000";
        String name = "Tester";
        String password = "test";

        User mockUser = User.builder().id(id).name(name).build();

        given(userService.authenticate(phone, password)).willReturn(mockUser);

        given(jwtUtil.createToken(id,name))
                .willReturn("header.payload.signature");

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"phone\":\"010-0000-0000\",\"password\":\"test\"}"))
                .andExpect(status().isCreated())
                .andExpect(header().string("location","/session"))
                .andExpect(content().string(
                        containsString("{\"accessToken\":\"header.payload.signature\"}")
                ));



        verify(userService).authenticate(eq(phone),eq(password));
       }

    @Test
    public void createWithWrongPassword() throws Exception {
        given(userService.authenticate("010-0000-0000","x"))
                .willThrow(PasswordWrongException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"phone\":\"010-0000-0000\",\"password\":\"x\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("010-0000-0000"),eq("x"));
    }

    @Test
    public void createWithNotExistedPhone() throws Exception {
        given(userService.authenticate("x","test"))
                .willThrow(PhoneNotExistedException.class);

        mvc.perform(post("/session")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"phone\":\"x\",\"password\":\"test\"}"))
                .andExpect(status().isBadRequest());

        verify(userService).authenticate(eq("x"),eq("test"));
    }
}
