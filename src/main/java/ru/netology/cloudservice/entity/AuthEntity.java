package ru.netology.cloudservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "AUTH_TOKENS")
@AllArgsConstructor
@NoArgsConstructor
public class AuthEntity {
    @Id
    private String token;

    @Column(name = "USER_ID")
    private long userId;
}
