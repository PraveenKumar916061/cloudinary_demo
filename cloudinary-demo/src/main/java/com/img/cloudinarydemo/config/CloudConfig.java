package com.img.cloudinarydemo.config;


import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudConfig {

    @Bean
    public Cloudinary cloudinary() {
        Map config = new HashMap();
        config.put("cloud_name", "dhmxqowtb");
        config.put("api_key", "465637731471621");
        config.put("api_secret", "YpI8RvNtSVq4JpvKlwG_FHjV4jg");
        config.put("secure",true);
        return new Cloudinary(config);
    }
}
