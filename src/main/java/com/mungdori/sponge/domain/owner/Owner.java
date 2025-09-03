package com.mungdori.sponge.domain.owner;


import com.mungdori.sponge.domain.AbstractEntity;
import com.mungdori.sponge.domain.shared.Detail;
import com.mungdori.sponge.domain.shared.Email;
import com.mungdori.sponge.domain.shared.GenderType;
import com.mungdori.sponge.domain.shared.UserStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.util.Assert;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

@Getter
@Entity
@ToString(callSuper = true, exclude = {"detail"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache
@Table(
        name = "owner",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_OWNER_EMAIL_ADDRESS", columnNames = "email_address"),
                @UniqueConstraint(name = "UK_OWNER_DETAIL_ID", columnNames = "detail_id")
        }
)
public class Owner extends AbstractEntity {

    @NaturalId
    @Embedded
    private Email email;

    @Column(name = "nickname", nullable = false, length = 100)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 50)
    private GenderType gender;

    @Column(name = "phone_number", nullable = false, length = 100)
    private String phoneNumber;

    @Column(name = "password_hash", nullable = false, length = 200)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private UserStatus status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_id")
    private Detail detail;

    public static Owner register(OwnerRegisterRequest registerRequest, PasswordEncoder passwordEncoder) {
        Owner owner = new Owner();

        owner.email = new Email(registerRequest.email());
        owner.nickname = requireNonNull(registerRequest.nickname());
        owner.gender = requireNonNull(registerRequest.gender());
        owner.phoneNumber = registerRequest.phoneNumber();
        owner.passwordHash = passwordEncoder.encode(requireNonNull(registerRequest.password()));
        owner.status = UserStatus.ACTIVE;
        owner.detail = Detail.create();

        return owner;
    }

    public void deactivate() {
        state(status == UserStatus.ACTIVE, "ACTIVE 상태가 아닙니다");

        this.status = UserStatus.DEACTIVATED;
        this.detail.deactivate();
    }

    public void updateInfo(OwnerInfoUpdateRequest updateRequest) {
        Assert.state(getStatus() == UserStatus.ACTIVE, "등록완료 상태가 아니면 정보를 수정할 수 없습니다.");

        this.nickname = requireNonNull(updateRequest.nickname());
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }


}
