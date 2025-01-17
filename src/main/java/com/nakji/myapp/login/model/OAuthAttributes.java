package com.nakji.myapp.login.model;

import com.nakji.myapp.user.model.User;
import lombok.Builder;

import java.util.Map;

@Builder
public record OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, User user) {
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) {
            return ofNaver(userNameAttributeName, attributes);
        } else {
            return null;
        }
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .user(User.builder()
                        .oauthId((String) response.get("id"))
                        .provider("naver")
                        .name((String) response.get("name"))
                        .nickname((String) response.get("nickname"))
                        .profileImage((String) response.get("profile_image"))
                        .email((String) response.get("email"))
                        .gender((String) response.get("gender"))
                        .birthYear((String) response.get("birthYear"))
                        .role(Role.USER)
                        .build())
                .build();
    }

    public User toEntity() {
        return user;
    }
}