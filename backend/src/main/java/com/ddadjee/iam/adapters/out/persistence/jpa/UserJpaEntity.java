package com.ddadjee.iam.adapters.out.persistence.jpa;

import com.ddadjee.iam.domain.Email;
import com.ddadjee.iam.domain.User;
import com.ddadjee.iam.domain.Username;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@Entity
@Table(name = "iam_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access= AccessLevel.PRIVATE)
public class UserJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "email", nullable = false, unique = true, length = 320)
    private String email;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "hashed_password", nullable = false, length = 100)
    private String hashedPassword;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    public static UserJpaEntity fromDomain(User user) {
        return new UserJpaEntity(
                user.getId(),
                user.getEmail().getValue(),
                user.getUsername().getValue(),
                user.getHashedPassword(),
                user.getCreatedAt()
        );
    }

    public User toDomain() {
        return User.rehydrate(
                id,
                new Email(email),
                new Username(username),
                hashedPassword,
                createdAt
        );
    }
}
