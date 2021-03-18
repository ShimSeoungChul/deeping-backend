package com.deeping.service;

import com.deeping.domain.User;
import com.deeping.domain.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.verify;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {

    private  UserService userService;

    @Mock
    private  UserRepository userRepository;

    @BeforeAll
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void getUsers(){

        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(User.builder()
                .phone("010-7756-1989")
                .name("테스터")
                .level(1L)
                .build());

        given(userRepository.findAll()).willReturn(mockUsers);

        List<User> users = userService.getUsers();
        User user = users.get(0);
        assertThat(user.getName()).isEqualTo("테스터");
    }

    @Test
    public void addUser(){
        String phone = "010-0000-0000";
        String name = "Administrator";

        User mockUser = User.builder().phone(phone).name(name).build();

        given(userRepository.save(any())).willReturn(mockUser);

        User user = userService.addUser(phone, name);

        assertThat(user.getName()).isEqualTo(name);
    }

    @Test
    public void updateUser(){
        Long id = 1004L;
        String phone = "010-0000-0000";
        String name = "Superman";
        Long level = 1004L;

        User mockUser = User.builder()
                .id(id)
                .name("Administrator")
                .level(1L)
                .build();

        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user = userService.updateUser(id, phone, name, level);
        verify(userRepository).findById(eq(id));

        assertThat(user.getName()).isEqualTo("Superman");
        assertThat(user.isAdamin()).isEqualTo(true);
    }

    @Test
    public void deactiveUser(){
        userService.deactiveUser(1004L);

        Long id = 1004L;

        User mockUser = User.builder()
                .id(id)
                .phone("010-0000-0000")
                .name("Administrator")
                .level(1004L)
                .build();
        given(userRepository.findById(id)).willReturn(Optional.of(mockUser));

        User user = userService.deactiveUser(1004L);

        verify(userRepository.findById(1004L));

        assertThat(user.isAdamin()).isEqualTo(false);
        assertThat(user.isActive()).isEqualTo(false);
    }

}