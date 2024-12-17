package com.hmsApp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JWTService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiry;

    private Algorithm algorithm; // it comes from com.auth0 Documentation
                                //it decides which algorithm used for encryption


    @PostConstruct    //this method should run automatically while starting the project
    public void postConstruct() throws UnsupportedEncodingException {
//        System.out.println(algorithmKey);
//        System.out.println(issuer);
//        System.out.println(expiry);   // To check this @PostConstruct is running while starting the project

        algorithm=Algorithm.HMAC256(algorithmKey); //algorithmKey is the secret password used by HMAC256 to
                                        // lock and verify the JWT, keeping it secure.

    }

    public String generateToken(String  username)
    {
        return JWT.create()
                .withClaim("name", username) //.withClaim() adds extra information (key-value pairs) to a JWT,// like username or role, for secure data sharing.
                .withExpiresAt(new Date(System.currentTimeMillis()+expiry))
                .withIssuer(issuer)
        .sign(algorithm);//here automatically come algorithm and algorithmKey


    }     //this method for generating a token from the given username

    public String getUsername(String token)//extract the username from the token
    {
        DecodedJWT decodedJWT = JWT.require(algorithm)//to decrypt the token algorithm which have secrete key
                .withIssuer(issuer)
                .build().verify(token);
        return decodedJWT.getClaim("name").asString(); //getClaim() extracts the value associated with the given key from the JWT.


    }
 }
