package com.deeping.service;

import com.deeping.domain.User;
import com.deeping.domain.UserRepository;
import com.deeping.web.PasswordWrongException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    private  UserService userService;

    @Mock
    private  UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeAll
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository,passwordEncoder);
    }

    @Test
    public void authenticateWithValidAttributes(){
        String phone = "010-0000-0000";
        String name = "Tester";
        String password = "test";

        //given
        User mockUser = User.builder()
                .phone(phone).build();
        given(userRepository.findByPhone(phone)).willReturn(Optional.of(mockUser));

        //when
        User user = userService.authenticate(phone, password);

        //then
        assertThat(user.getPhone()).isEqualTo(phone);
    }

    @Test
    public void authenticateWithNotExistedPhone(){
        String phone = "x";
        String password = "test";

        //given
        User mockUser = User.builder()
                .phone(phone).build();
        given(userRepository.findByPhone(phone)).willReturn(Optional.empty());

        //예외 처리
        Exception exception = assertThrows(PhoneNotExistedException.class, () -> {
            //when
            User user = userService.authenticate(phone, password);

            //then
            assertThat(user.getPhone()).isEqualTo(phone);
        });
    }

    @Test
    public void authenticateWithWrongPassword(){
        String phone = "010-0000-0000";
        String password = "x";

        //given
        User mockUser = User.builder()
                .phone(phone).build();
        given(userRepository.findByPhone(phone)).willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(),any())).willReturn(false);

        //예외 처리
        Exception exception = assertThrows(PasswordWrongException.class, () -> {
            //when
            User user = userService.authenticate(phone, password);

            //then
            assertThat(user.getPhone()).isEqualTo(phone);
        });
    }
}