package com.hmsApp.controller;


import com.hmsApp.entity.User;
import com.hmsApp.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private BucketService bucketService;

    public ImageController(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}",
            consumes= MediaType.MULTIPART_FORM_DATA_VALUE, produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadPropertyPhotos(@RequestParam MultipartFile file,
                                                  @PathVariable String bucketName,
                                                  @PathVariable long propertyId
                                                ) throws IllegalAccessException {
      String imageUrl=bucketService.uploadFile(file,bucketName);
      return new ResponseEntity<>(imageUrl, HttpStatus.OK);
    }


}
