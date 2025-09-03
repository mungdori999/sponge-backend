package com.mungdori.sponge.domain.trainer;

import com.mungdori.sponge.domain.AbstractEntity;
import com.mungdori.sponge.domain.owner.PasswordEncoder;
import com.mungdori.sponge.domain.shared.Detail;
import com.mungdori.sponge.domain.shared.Email;
import com.mungdori.sponge.domain.shared.GenderType;
import com.mungdori.sponge.domain.shared.UserStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "trainer",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_TRAINER_EMAIL_ADDRESS", columnNames = "email_address"),
        }
)
public class Trainer extends AbstractEntity {

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

    @Column(name = "introduction", nullable = false, length = 100)
    private String introduction;

    @Column(name = "employment_year", nullable = false)
    private int employmentYear;

    @Column(name = "password_hash", nullable = false, length = 200)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private UserStatus status;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_id")
    private Detail detail;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "trainer")
    private List<History> historyList = new ArrayList<>();


    public static Trainer register(TrainerRegisterRequest registerRequest, PasswordEncoder passwordEncoder) {
        Trainer trainer = new Trainer();

        trainer.email = new Email(requireNonNull(registerRequest.email()));
        trainer.nickname = requireNonNull(registerRequest.nickname());
        trainer.gender = requireNonNull(registerRequest.gender());
        trainer.phoneNumber = requireNonNull(registerRequest.phoneNumber());
        trainer.introduction = registerRequest.introduction();
        trainer.employmentYear = registerRequest.employmentYear();
        trainer.passwordHash = passwordEncoder.encode(requireNonNull(registerRequest.password()));

        trainer.status = UserStatus.ACTIVE;
        trainer.detail = Detail.create();

        registerRequest.historyCreateRequestList().forEach(history -> {
            History.create(history, trainer);
        });
        return trainer;
    }

    public void updateInfo(TrainerUpdateRequest updateRequest) {
        state(status == UserStatus.ACTIVE, "ACTIVE 상태가 아닙니다");

        this.nickname = requireNonNull(updateRequest.nickname());
        this.gender = requireNonNull(updateRequest.gender());
        this.phoneNumber = requireNonNull(updateRequest.phoneNumber());
        this.introduction = requireNonNull(updateRequest.introduction());
        this.employmentYear = updateRequest.employmentYear();

        historyList.clear();

        updateRequest.historyCreateRequestList().forEach(history -> {
            History.create(history, this);
        });
    }

    public void deactivate() {
        state(status == UserStatus.ACTIVE, "ACTIVE 상태가 아닙니다");

        this.status = UserStatus.DEACTIVATED;
        this.detail.deactivate();
    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.passwordHash);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }


}
