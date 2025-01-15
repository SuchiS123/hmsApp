package com.hmsApp.controller;


import com.hmsApp.entity.User;
import com.hmsApp.payload.JwtToken;
import com.hmsApp.payload.LoginDto;
import com.hmsApp.payload.ProfileDto;
import com.hmsApp.payload.UserDto;
import com.hmsApp.repository.UserRepository;
import com.hmsApp.service.JWTService;
import com.hmsApp.service.OTPServiceHandler;
import com.hmsApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private UserService userService;;
    private UserRepository userRepository;
    private OTPServiceHandler otpServiceHandler;
    private JWTService jwtService;

    public AuthController(UserService userService, UserRepository userRepository,
                          OTPServiceHandler otpServiceHandler,
                          JWTService jwtService)
    {
        this.userService = userService;
        this.userRepository = userRepository;
        this.otpServiceHandler = otpServiceHandler;
        this.jwtService = jwtService;
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

    @PostMapping("/otp/send")
    public ResponseEntity<String> sendOtp(@RequestParam String mobile) {
        // Send OTP
        String sid = otpServiceHandler.sendOtp(mobile);
        return new ResponseEntity<>("OTP sent successfully with SID: " + sid, HttpStatus.OK);
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

    @GetMapping("/user/profile")
    public ResponseEntity<ProfileDto> getUserProfile(@AuthenticationPrincipal User user)
    {
        ProfileDto profileDto = new ProfileDto();
        profileDto.setUsername(user.getUsername());
        profileDto.setEmail(user.getEmail());
        profileDto.setName(user.getName());
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }


}
