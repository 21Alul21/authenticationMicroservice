package com.authentication.microservice.entity;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

public class PasswordResetEntity {
@Id
@GeneratedValue(strategy = GenerationType.UUID)
private UUID resetId;
private String RestToken;
private Instant tokenCreatedAt;
private Instant tokenExpiryData;

@OneToOne
@JoinColumn(name = "user_id", unique = true)
private UserEntity user;
}
