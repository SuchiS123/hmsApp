package com.hmsApp.config;

import com.hmsApp.service.JWTService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private JwtFilter jwtFilter;
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http.csrf().disable().cors().disable();// h(cd)2 //Cross-Site Request Forgery(csrf())
        http.addFilterBefore(jwtFilter, AuthorizationFilter.class);

//        http.authorizeHttpRequests().anyRequest().permitAll();  //haap
        http.authorizeHttpRequests().requestMatchers("/api/auth/sign-up","/api/auth/login","/api/auth/property/sign-up","/api/v1/city/add/city",
                "/api/v1/country/add/country","/api/v1/property/{searchParam}","/api/v1/property/get/property/images","/api/v1/rooms/search/rooms")
                .permitAll().
                requestMatchers("/api/v1/property/addProperty","/api/v1/property/addProperty/{country}/{city}",
                "/api/v1/property/updateProperty/{id}/{country}/{city}","/api/v1/images/upload/file/{bucketName}/property/{propertyId}")
                .hasRole("OWNER")
                .requestMatchers("/api/v1/property/deleteProperty","/api/v1/property/deleteProperty/{name}",
                        "/api/v1/property/deletePropertyByCity/{cityId}","/api/v1/property/upload/photos/{bucketName}/{propertyId}")
                .hasAnyRole("OWNER","ADMIN")
                .requestMatchers("/api/auth/blog/sign-up")
                .hasRole("ADMIN")
                .anyRequest().authenticated();

        return http.build();  // it return to spring framework
        //Builds and returns the configured SecurityFilterChain object.

    }
}
