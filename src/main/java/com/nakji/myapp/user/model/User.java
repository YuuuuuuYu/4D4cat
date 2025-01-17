package com.nakji.myapp.user.model;

import com.nakji.myapp.common.entity.BaseTimeEntity;
import com.nakji.myapp.login.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length=150)
    private String oauthId;

    @Column(nullable=false)
    private String provider;

    @Column(length=30)
    private String name;

    @Column(length=30)
    private String nickname;

    @Column(length=200)
    private String profileImage;

    @Column(length=30)
    private String email;

    @Column(length=1)
    private String gender;

    @Column(length=10)
    private String birthYear;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User update(String nickname, String profileImage, String email) {
        this.nickname = nickname;
        this.profileImage = profileImage;
        this.email = email;

        return this;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}