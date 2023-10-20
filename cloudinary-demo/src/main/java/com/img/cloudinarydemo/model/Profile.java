package com.img.cloudinarydemo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer profileId;
    private String profileName;
    private Integer age;
    private String phoneNumber;
    private String email;
    private String password;
    private String imageUrl;
    private String imageId;

}

