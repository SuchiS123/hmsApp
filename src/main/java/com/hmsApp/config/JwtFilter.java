package com.hmsApp.config;

import com.hmsApp.entity.User;
import com.hmsApp.repository.UserRepository;
import com.hmsApp.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component

public class JwtFilter extends OncePerRequestFilter { //this OncePerRequestFilter is abstract class
//   having incomplete methods that one "doFilterInternal"
    //url with token autmatically come to request object .

    private JWTService jwtService;
    private UserRepository userRepository;
    public JwtFilter(JWTService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;  // injecting user repository here.  // userRepository is used to fetch user by username.  // this is done in the constructor of JwtFilter class.  // the userRepository is injected into the JwtFilter constructor.  // this is a dependency injection.  // dependency injection is a design pattern where an object receives the dependencies (other objects) through its constructor, setter methods, or configuration properties.  // in this case, the User
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = request.getHeader("Authorization");

        if(token!=null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(8, token.length() - 1);
            String username = jwtService.getUsername(jwtToken);

            Optional<User> opUser= userRepository.findByUsername(username);
            if(opUser.isPresent()) {
                User user =opUser.get(); //this user having additional parameters is Roll
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, null, Collections.singleton(new SimpleGrantedAuthority(user.getRole())));
                authenticationToken.setDetails(new WebAuthenticationDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                // setting the authentication token to the current thread's security context.  // this is how Spring Security keeps track of the authenticated user.  // the authentication token is stored in the thread's security context.  // the SecurityContextHolder is a class that provides a centralized way to access and modify the security context.  // the SecurityContextHolder class is used to manage the security context for the current thread.  // the Security

            }

        }
        filterChain.doFilter(request,response);
    }

}
