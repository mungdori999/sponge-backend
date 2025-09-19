package com.mungdori.sponge.domain.pet;

import com.mungdori.sponge.domain.AbstractEntity;
import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.shared.GenderType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.util.Objects.requireNonNull;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet extends AbstractEntity {

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "breed", nullable = false, length = 100)
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 50)
    private GenderType gender;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "weight", nullable = false)
    private float weight;

    @Column(name = "owner_id")
    private Long ownerId;


    public static Pet register(PetRegisterRequest registerRequest, Long ownerId) {
        Pet pet = new Pet();

        pet.name = requireNonNull(registerRequest.name());
        pet.breed = requireNonNull(registerRequest.breed());
        pet.gender = requireNonNull(registerRequest.gender());
        pet.age = registerRequest.age();
        pet.weight = registerRequest.weight();
        pet.ownerId = ownerId;

        return pet;
    }

    public void updateInfo(PetInfoUpdateRequest petInfoUpdateRequest) {

        this.name = requireNonNull(petInfoUpdateRequest.name());
        this.breed = requireNonNull(petInfoUpdateRequest.breed());
        this.gender = requireNonNull(petInfoUpdateRequest.gender());
        this.age = petInfoUpdateRequest.age();
        this.weight = petInfoUpdateRequest.weight();
    }
}
