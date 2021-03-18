package com.deeping.web;

import com.deeping.domain.User;
import com.deeping.service.UserService;
import com.deeping.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class SessionController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userServie;

    @PostMapping("/session")
    public ResponseEntity<SessionResponseDto> create(
            @RequestBody SessionRequestDto resource
    ) throws URISyntaxException {

        String email = resource.getPhone();
        String password = resource.getPassword();

        User user = userServie.authenticate(email, password);

        String accessToken = jwtUtil.createToken(user.getId(), user.getName());

        SessionResponseDto sessionDto = SessionResponseDto.builder()
                .accessToken(accessToken)
                .build();

        String url = "/session";
        return ResponseEntity.created(new URI(url))
                .body(sessionDto);
    }
}
