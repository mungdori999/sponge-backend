package com.mungdori.sponge.domain.trainer;

import com.mungdori.sponge.domain.AbstractEntity;
import com.mungdori.sponge.domain.shared.Email;
import com.mungdori.sponge.domain.shared.GenderType;
import com.mungdori.sponge.domain.shared.UserStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "trainer",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_OWNER_EMAIL_ADDRESS", columnNames = "email_address"),
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private UserStatus status;

}
