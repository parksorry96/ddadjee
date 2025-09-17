package com.ddadjee.iam.domain;

import lombok.AccessLevel;
import lombok.Getter;

import java.time.Clock;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public final class User {
    private final UUID id;
    private final Email email;
    private final Username username;
    private final String hashedPassword;
    private final Instant createdAt;
    @Getter(AccessLevel.NONE)
    private final List<Object> domainEvents = new ArrayList<>();

    private User(UUID id, Email email, Username username, String hashedPassword, Instant createdAt) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.createdAt = createdAt;
    }

    /**
     * 회원가입 스태틱 메소드
     * @param email 이메일
     * @param username 사용자명(ID)
     * @param hashedPassword 비밀번호
     * @param clock 시간
     * @return 생성된 user aggregate
     * @throws IllegalArgumentException parameter 검증 실패시
     */
    public static User registerNew(
            Email email, Username username, String hashedPassword, Clock clock
    ){
        if(email == null){
            throw new IllegalArgumentException("Email cannot be null");
        }
        if(username == null){
            throw new IllegalArgumentException("Username cannot be null");
        }
        if(hashedPassword == null){
            throw new IllegalArgumentException("Hashed password cannot be null");
        }
        UUID id = UUID.randomUUID();
        Instant now = Instant.now(clock);

        User user = new User(id, email, username, hashedPassword, now);

        user.domainEvents.add(new UserRegisteredEvent(
                id,email.getValue(),username.getValue(),now
        ));
        return user;
    }

    /**
     * 적제된 이벤트 반환, 버퍼 clear
     * @return 적재되어 있던 이벤트 목록
     */
    public List<Object> pullDomainEvents(){
        List<Object> out= new ArrayList<>(domainEvents);
        domainEvents.clear();
        return out;
    }

    /**
     * Persistence -> Domain 복원용 메소드
     */
    public static User rehydrate(UUID id, Email email, Username username, String hashedPassword, Instant createdAt){
        if(id==null)throw new IllegalArgumentException("id cannot be null");
        if(email==null)throw new IllegalArgumentException("email cannot be null");
        if(username==null)throw new IllegalArgumentException("username cannot be null");
        if(hashedPassword==null)throw new IllegalArgumentException("hashed password cannot be null");
        if(createdAt==null)throw new IllegalArgumentException("createdAt cannot be null");
        return new User(id, email, username, hashedPassword, createdAt);

    }
}
