package com.mungdori.sponge.domain.owner;


import com.mungdori.sponge.domain.shared.Email;
import com.mungdori.sponge.domain.shared.UserStatus;
import lombok.Getter;
import lombok.ToString;
import org.springframework.util.Assert;

import java.util.Objects;

import static org.springframework.util.Assert.state;

@Getter
@ToString
public class Owner {

    private Long id;

    private Email email;

    private String name;
    private String passwordHash;

    private UserStatus status;
    private OwnerDetail detail;

    public static Owner register(OwnerRegisterRequest registerRequest, PasswordEncoder passwordEncoder) {
        Owner owner = new Owner();

        owner.email = new Email(registerRequest.email());
        owner.name = Objects.requireNonNull(registerRequest.name());
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

        this.name = Objects.requireNonNull(updateRequest.name());
    }


}
