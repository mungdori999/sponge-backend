package com.mungdori.sponge.domain.owner;


import com.mungdori.sponge.domain.AbstractEntity;
import com.mungdori.sponge.domain.shared.Email;
import com.mungdori.sponge.domain.shared.UserStatus;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.util.Assert;

import java.util.Objects;

import static org.springframework.util.Assert.state;

@Getter
@Entity
@ToString(callSuper = true, exclude = "detail")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache
public class Owner extends AbstractEntity {

    @NaturalId
    private Email email;

    private String nickName;
    private String passwordHash;

    private UserStatus status;
    private OwnerDetail detail;

    public static Owner register(OwnerRegisterRequest registerRequest, PasswordEncoder passwordEncoder) {
        Owner owner = new Owner();

        owner.email = new Email(registerRequest.email());
        owner.nickName = Objects.requireNonNull(registerRequest.nickName());
        owner.passwordHash = passwordEncoder.encode(Objects.requireNonNull(registerRequest.password()));
        owner.status = UserStatus.PENDING;
        owner.detail = OwnerDetail.create();
        return owner;
    }

    public void activate() {
        state(status == UserStatus.PENDING, "PENDING 상태가 아닙니다");

        this.status = UserStatus.ACTIVE;
        this.detail.activate();
    }

    public void deactivate() {
        state(status == UserStatus.ACTIVE, "ACTIVE 상태가 아닙니다");

        this.status = UserStatus.DEACTIVATED;
        this.detail.deactivate();
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void updateInfo(OwnerInfoUpdateRequest updateRequest) {
        Assert.state(getStatus() == UserStatus.ACTIVE, "등록완료 상태가 아니면 정보를 수정할 수 없습니다.");

        this.nickName = Objects.requireNonNull(updateRequest.name());
    }


}
