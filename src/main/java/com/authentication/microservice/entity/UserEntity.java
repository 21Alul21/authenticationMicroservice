package com.authentication.microservice.entity;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.authentication.microservice.enums.RoleEnum;


import java.time.Instant;

import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;

import lombok.AllArgsConstructor;
import jakarta.persistence.EnumType;
import jakarta.persistence.GenerationType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;



@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private Instant createdAt;
    private boolean isActive;
    private String firstName;
    private String lastName;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private PasswordResetEntity passwordResetEntity;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<RefreshTokenEntity> refreshTokenEntity = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();

        if (role == null){
            this.role = RoleEnum.USER;
        }

        this.isActive = true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email; // username = email
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }


    // helper methods for adding and removing user refresh tokens
    public void addRefreshToken(RefreshTokenEntity token){
        refreshTokenEntity.add(token);
        token.setUserEntity(this);
    }

    public void removeRefreshToken(RefreshTokenEntity token){
        token.setUserEntity(null);
    }
    
}
