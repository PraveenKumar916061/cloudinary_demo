package com.img.cloudinarydemo.controller;

import com.img.cloudinarydemo.model.Profile;
import com.img.cloudinarydemo.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<?> signUp(@RequestPart("file") @Valid MultipartFile file, @RequestPart("profile") Profile profile) throws Exception {
        profileService.signUp(file,profile);
        return ResponseEntity.status(HttpStatus.OK).body("successfully saved the image.");
    }

    @DeleteMapping("/delete-profile/{profile-id}")
    public ResponseEntity<?> deleteProfile(@PathVariable("profile-id") Integer profileId) throws Exception {
        profileService.deleteProfile(profileId);
        return ResponseEntity.status(HttpStatus.OK).body("deleted successfully.....!");
    }

    @PutMapping("/update-profile/{profile-id}")
    public ResponseEntity<?> updateProfile(@PathVariable("profile-id") Integer profileId, @RequestPart MultipartFile file) throws IOException {
        profileService.updateProfile(profileId, file);
        return ResponseEntity.status(HttpStatus.OK).body("updated successfully.....!");
    }
}
