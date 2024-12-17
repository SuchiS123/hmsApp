package com.hmsApp.controller;


import com.hmsApp.payload.JwtToken;
import com.hmsApp.payload.LoginDto;
import com.hmsApp.payload.UserDto;
import com.hmsApp.repository.UserRepository;
import com.hmsApp.service.UserService;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;;
    private UserRepository userRepository;

    public AuthController(UserService userService, UserRepository userRepository)
    {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    //Used for user Sign-Up
    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(@RequestBody UserDto userdto)
    {
        ResponseEntity<?> responseEntity= userService.createUser(userdto);
        return new ResponseEntity<>(responseEntity,HttpStatus.CREATED);

    }

    @PostMapping("/property/sign-up")
    public ResponseEntity<?> createPropertyOwnerAccount(@RequestBody UserDto userdto)
    {
        ResponseEntity<?> responseEntity= userService.createPropertyOwner(userdto);
        return new ResponseEntity<>(responseEntity,HttpStatus.CREATED);

    }

    @PostMapping("/blog/sign-up")
    public ResponseEntity<?> createBlogManagerAccount(@RequestBody UserDto userdto)
    {
        ResponseEntity<?> responseEntity= userService.createBlogManagerAccount(userdto);
        return new ResponseEntity<>(responseEntity,HttpStatus.CREATED);

    }

    @PostMapping ("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto)
    {
        String token = userService.verifyLogin(loginDto);
        JwtToken jwtToken=new JwtToken();
        jwtToken.setToken(token);
        jwtToken.setType("JWT");
        if(token!=null)
        {
            return new ResponseEntity<>(jwtToken,HttpStatus.OK);
        }
        return new ResponseEntity<>("Invalid login and Username or Password",HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
