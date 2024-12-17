package com.hmsApp.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtToken {

    private String token;
    private String type;
}
