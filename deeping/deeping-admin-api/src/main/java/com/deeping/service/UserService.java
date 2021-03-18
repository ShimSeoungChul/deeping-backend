package com.deeping.service;

import com.deeping.domain.User;
import com.deeping.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getUsers() {
        List<User> users = userRepository.findAll();

        return users;
    }

    public User updateUser(Long id, String phone, String name, Long level){
        //TODO: restaurantService의 예외 처리 참고.
        User user = userRepository.findById(id).orElse(null);
        user.setName(name);
        user.setLevel(level);

        return user;
    }

    public User addUser(String phone, String name) {
        User user = User.builder()
                .phone(phone)
                .name(name)
                .level(1L)
                .build();
        return userRepository.save(user);
    }

    public User deactiveUser(long id) {
       //TODO: 실제로 작업 필요합
        User user = userRepository.findById(id).orElse(null);
        user.deactive();
        return user;
    }
}
