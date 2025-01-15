package com.hmsApp.service;

import com.hmsApp.entity.User;
import com.hmsApp.payload.LoginDto;
import com.hmsApp.payload.UserDto;
import com.hmsApp.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private JWTService jwtService;
    private OTPServiceHandler otpServiceHandler;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, JWTService jwtService,
                       OTPServiceHandler otpServiceHandler) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.otpServiceHandler = otpServiceHandler;
    }

    public ResponseEntity<?> createUser(UserDto userdto) {

        User user=mapToEntity(userdto);
        Optional<User> opEmail = userRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()) {
            return new ResponseEntity<>("Email already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()) {
            return new ResponseEntity<>("UserName already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opMobile = userRepository.findByMobile(user.getMobile());
        if(opMobile .isPresent()) {
            return new ResponseEntity<>("Mobile already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));
        user.setRole("ROLE_USER");
        User savedUser = userRepository.save(user);
        UserDto userDto = mapToDto(savedUser);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

    public ResponseEntity<?> createPropertyOwner(UserDto userdto) {
        User user=mapToEntity(userdto);
        Optional<User> opEmail = userRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()) {
            return new ResponseEntity<>("Email already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()) {
            return new ResponseEntity<>("UserName already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opMobile = userRepository.findByMobile(user.getMobile());
        if(opMobile .isPresent()) {
            return new ResponseEntity<>("Mobile already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));
        user.setRole("ROLE_OWNER");
        User savedUser = userRepository.save(user);
        UserDto userDto = mapToDto(savedUser);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }

    public ResponseEntity<?> createBlogManagerAccount(UserDto userdto) {
        User user=mapToEntity(userdto);
        Optional<User> opEmail = userRepository.findByEmail(user.getEmail());
        if(opEmail.isPresent()) {
            return new ResponseEntity<>("Email already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opUsername = userRepository.findByUsername(user.getUsername());
        if(opUsername.isPresent()) {
            return new ResponseEntity<>("UserName already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Optional<User> opMobile = userRepository.findByMobile(user.getMobile());
        if(opMobile .isPresent()) {
            return new ResponseEntity<>("Mobile already exists", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        user.setPassword(BCrypt.hashpw(user.getPassword(),BCrypt.gensalt(10)));
        user.setRole("ROLE_BLOGMANAGER");
        User savedUser = userRepository.save(user);
        UserDto userDto = mapToDto(savedUser);
        return new ResponseEntity<>(userDto, HttpStatus.OK);

    }



    public User mapToEntity(UserDto userdto)
    {
        User user=modelMapper.map(userdto, User.class);

        return user;

    }

    public UserDto mapToDto(User user)
    {
        UserDto userDto=modelMapper.map(user, UserDto.class);

        return userDto;

    }

//    verify username and password to login
    public String verifyLogin(LoginDto loginDto) {

        boolean isOtpValid = otpServiceHandler.verifyOtp(loginDto.getOtp()); // Assuming LoginDto has an otp field
        if (!isOtpValid) {
            throw new IllegalArgumentException("Invalid OTP");
        }

//        The error occurs because userRepository.findByUsername()
//        is written to return an Optional<User>, not an Optional<UserDto>.


        Optional<User> opUsername = userRepository.findByUsername(loginDto.getUsername());
        if (opUsername.isPresent()) {
            User user = opUsername.get();
            if (BCrypt.checkpw(loginDto.getPassword(), user.getPassword())) {
                String token = jwtService.generateToken(user.getUsername());
                return token;

            }

        } else {
            return null;
        }
        return null;
    }



}