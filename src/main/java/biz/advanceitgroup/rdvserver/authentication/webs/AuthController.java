package biz.advanceitgroup.rdvserver.authentication.webs;

import biz.advanceitgroup.rdvserver.authentication.exception.BadRequestException;
import biz.advanceitgroup.rdvserver.authentication.entities.AuthProvider;
import biz.advanceitgroup.rdvserver.authentication.entities.User;
import biz.advanceitgroup.rdvserver.authentication.payload.ApiResponse;
import biz.advanceitgroup.rdvserver.authentication.payload.AuthResponse;
import biz.advanceitgroup.rdvserver.authentication.payload.LoginRequest;
import biz.advanceitgroup.rdvserver.authentication.payload.SignUpRequest;
import biz.advanceitgroup.rdvserver.authentication.repository.UserRepository;
import biz.advanceitgroup.rdvserver.authentication.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setEncryptPwd(signUpRequest.getPassword());
        user.setProvider(AuthProvider.local);
        
        user.setEncryptPwd(passwordEncoder.encode(user.getEncryptPwd()));

        

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully@"));
    }

}
