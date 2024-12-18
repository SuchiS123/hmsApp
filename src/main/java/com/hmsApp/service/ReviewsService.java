package com.hmsApp.service;

import com.hmsApp.entity.Property;
import com.hmsApp.entity.Reviews;
import com.hmsApp.entity.User;
import com.hmsApp.payload.ReviewsDto;
import com.hmsApp.repository.PropertyRepository;
import com.hmsApp.repository.ReviewsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewsService {
    private ReviewsRepository reviewsRepository;
    private PropertyRepository propertyRepository;
    private ModelMapper modelMapper;

    public ReviewsService(ReviewsRepository reviewsRepository,
                          PropertyRepository propertyRepository, ModelMapper modelMapper) {
        this.reviewsRepository = reviewsRepository;
        this.propertyRepository = propertyRepository;
        this.modelMapper = modelMapper;
    }

    public String addReview(ReviewsDto reviewsDto, Long propertyId, User user) {
        Property property = propertyRepository.findById(propertyId).get();
        Reviews reviewsStatus = reviewsRepository.findByPropertyAndUser(property, user);
        if (reviewsStatus != null) {


            Reviews reviews = mapToEntity(reviewsDto);
            reviews.setProperty(property);
            reviews.setUser(user);
            Reviews savedReview = reviewsRepository.save(reviews);
            ReviewsDto reviewsDto1 = mapToDto(savedReview);
            return "added review";
        }
       return "Review alredy given";

    }



    public Reviews mapToEntity(ReviewsDto reviewsDto) {
        Reviews mapReview = modelMapper.map(reviewsDto, Reviews.class);
        return mapReview;
    }

    public ReviewsDto mapToDto(Reviews reviews)
    {
        ReviewsDto mapReviewDto = modelMapper.map(reviews, ReviewsDto.class);
        return mapReviewDto;
    }

    public List<Reviews> viewUserReviews(User user) {
        List<Reviews> findByUser = reviewsRepository.findByUser(user);
        return findByUser;

    }
}
