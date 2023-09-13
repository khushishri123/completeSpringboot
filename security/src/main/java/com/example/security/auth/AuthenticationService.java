package com.example.security.auth;

import com.example.security.config.JwtService;
import com.example.security.user.Role;
import com.example.security.user.User;
import com.example.security.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest registerRequest)
    {
    // we have to build our User out of this request.
        var user= User.builder ()
                .firstName ( registerRequest.getFirstName () )
                .lastName ( registerRequest.getLastName () )
                .email ( registerRequest.getEmail () )
                .password (passwordEncoder.encode ( registerRequest.getPassword () ))
                .role( Role.USER)
                .build ();
        userRepository.save(user);
        //since we are registering the user , we have to save the user in our db and also we have to generate the
        // token for the user.
        var jwtToken=jwtService.generateToken ( user );
        return AuthenticationResponse.
                builder ().
                token ( jwtToken ).
                build ();
    }
    public  AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest)
    {
        System.out.println ("ndsjndfsjdnjsk");
        System.out.println ("Email: "+authenticationRequest.getEmail ());
        System.out.println ("Password: "+authenticationRequest.getPassword ());
        authenticationManager.authenticate (
          new UsernamePasswordAuthenticationToken (
                  authenticationRequest.getEmail (),
                  authenticationRequest.getPassword ()
          )
        );
        System.out.println ("dsdsjndfsdsdsjdnjsk");
        var user=userRepository.
                findByEmail ( authenticationRequest.getEmail () ).
                orElseThrow ();
        System.out.println ("Excfdfdfd");
        var jwtToken=jwtService.generateToken ( user );
        return AuthenticationResponse.
                builder ().
                token ( jwtToken ).
                build ();
    }
}
