package com.deeping.service;

import com.deeping.domain.User;
import com.deeping.domain.UserRepository;
import com.deeping.web.PasswordWrongException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User registerUser(String phone, String name, String password) {
        Optional <User> existed = userRepository.findByPhone(phone);

        //중복 회원가입 방지
        if(existed.isPresent()){
            throw new PhoneExistedException(phone);
        }

        System.out.println("abcd");

        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .phone(phone)
                .name(name)
                .password(encodedPassword)
                .level(1L)
                .build();

        return userRepository.save(user);
    }


    public User authenticate(String phone, String password) {
        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new PhoneNotExistedException(phone));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new PasswordWrongException(phone, password);
        }

        return user;
    }

}
