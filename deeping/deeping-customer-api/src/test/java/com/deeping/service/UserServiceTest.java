package com.deeping.service;

import com.deeping.domain.User;
import com.deeping.domain.UserRepository;
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
import static org.mockito.ArgumentMatchers.eq;
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
    public void registerUser(){
        String phone = "010-0000-0000";
        String name = "Tester";
        String password = "test";

        userService.registerUser(phone, name, password);

        verify(userRepository).save(any());
    }

    @Test
    public void registerUserWithExistedPhone(){

        String phone = "010-0000-0000";
        String name = "Tester";
        String password = "test";


        User mockUser = User.builder().build();
        given(userRepository.findByPhone(phone)).willReturn(Optional.of(mockUser));
        given(passwordEncoder.matches(any(),any())).willReturn(true);


        //예외 처리
        Exception exception = assertThrows(PhoneExistedException.class, () -> {
            userService.registerUser(phone, name, password);
        });

        String expectMessage ="Phone is already registed";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectMessage));

        // 중복된 전화번호는 회원가입 실행 안함
       verify(userRepository, never()).save(any());
    }


}