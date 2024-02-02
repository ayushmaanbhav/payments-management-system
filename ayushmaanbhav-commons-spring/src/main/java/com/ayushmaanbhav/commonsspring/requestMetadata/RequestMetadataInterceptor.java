package com.ayushmaanbhav.commonsspring.requestMetadata;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RequestMetadataInterceptor implements WebMvcConfigurer {
    RequestMetadataHandler requestMetadataHandler = new RequestMetadataHandler();

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(requestMetadataHandler);
    }
}
