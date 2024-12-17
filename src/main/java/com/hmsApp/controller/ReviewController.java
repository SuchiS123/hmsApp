package com.hmsApp.controller;

import com.hmsApp.entity.Reviews;
import com.hmsApp.entity.User;
import com.hmsApp.payload.ReviewsDto;
import com.hmsApp.service.ReviewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private ReviewsService reviewsService;

    public ReviewController(ReviewsService reviewsService) {
        this.reviewsService = reviewsService;
    }

    //after login throught jwt token then use postmapping for review
    @PostMapping
    public ResponseEntity<String> addReview(@RequestBody ReviewsDto reviewsDto,
                                           @RequestParam Long propertyId, @AuthenticationPrincipal User user)
    {

        ReviewsDto reviewsDto1 = reviewsService.addReview(reviewsDto, propertyId, user);
        return ResponseEntity.ok("Review added successfully");

    }

    @GetMapping("/user/reviews")
    public ResponseEntity<List<Reviews>> viewMyReviews(@AuthenticationPrincipal User user)
    {

        List<Reviews> reviews = reviewsService.viewUserReviews(user);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }


}
