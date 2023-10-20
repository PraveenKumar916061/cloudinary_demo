package com.img.cloudinarydemo.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.img.cloudinarydemo.exception.InvalidEmailException;
import com.img.cloudinarydemo.exception.InvalidPhoneNumberException;
import com.img.cloudinarydemo.model.Profile;
import com.img.cloudinarydemo.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ProfileService {
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private ProfileRepository profileRepository;

    Pattern phonePattern = Pattern.compile("[7-9][0-9]{9}");
    Pattern emailPattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9_.]*@gmail.com");

    /**** signup Profile ****/
    public String signUp(MultipartFile file, Profile profile) throws Exception {
        Matcher phoneMatcher = phonePattern.matcher(profile.getPhoneNumber());
        if (phoneMatcher.matches())
            profile.setPhoneNumber(profile.getPhoneNumber());
        else
            throw new InvalidPhoneNumberException("Please check the mobile number.....!");
        Matcher emailMatcher = emailPattern.matcher(profile.getEmail());
        if (emailMatcher.matches())
            profile.setEmail(profile.getEmail());
        else
            throw new InvalidEmailException("Please check the email address.....!");

        Map uploadedImage = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
        profile.setImageUrl((String) uploadedImage.get("url"));
        profile.setImageId((String) uploadedImage.get("public_id"));
        profileRepository.save(profile);
        return "Saved successfully";
    }

    /**** deletion of an existing image ****/
    public String deleteProfile(Integer profileId) throws IOException {
        Optional<Profile> optionalProfile = profileRepository.findById(profileId);
        if (optionalProfile.isEmpty())
            throw new RuntimeException("Id not found....!");
        else {
            Profile profile = optionalProfile.get();
            cloudinary.uploader().destroy(profile.getImageId(), ObjectUtils.emptyMap());
            profile.setImageUrl(null);
            profile.setImageId(null);
            profileRepository.save(profile);
            return "deleted successfully....!";
        }
    }

    /**** updation of an existing image ****/
    public String updateProfile(Integer profileId, MultipartFile file) throws IOException {
        Optional<Profile> optionalProfile = profileRepository.findById(profileId);
        if (optionalProfile.isEmpty())
            throw new RuntimeException("Id not found.....!");
        else {
            Profile profile = optionalProfile.get();
            if (profile.getImageId() == null) {
                Map uploadedImage = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                profile.setImageId((String) uploadedImage.get("public_id"));
                profile.setImageUrl((String) uploadedImage.get("url"));
                profileRepository.save(profile);
            } else {
                cloudinary.uploader().destroy(profile.getImageId(), ObjectUtils.emptyMap());
                Map uploadedImage = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                profile.setImageId((String) uploadedImage.get("public_id"));
                profile.setImageUrl((String) uploadedImage.get("url"));
                profileRepository.save(profile);
            }
            return "updated successfully......!";
        }
    }
}
