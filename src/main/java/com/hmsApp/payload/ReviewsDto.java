package com.hmsApp.payload;

import com.hmsApp.entity.Property;
import com.hmsApp.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewsDto {

    private Long id;
    private Integer rating;
    private String description;
    private Property property;
    private User user;
}
