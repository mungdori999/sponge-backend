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

    private String name;

    private String breed;

    @Enumerated(EnumType.STRING)
    private GenderType gender;

    private int age;

    private float weight;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;


    public static Pet register(PetRegisterRequest registerRequest) {
        Pet pet = new Pet();

        pet.name = requireNonNull(registerRequest.name());
        pet.breed = requireNonNull(registerRequest.breed());
        pet.gender = requireNonNull(registerRequest.gender());
        pet.age = registerRequest.age();
        pet.weight = registerRequest.weight();

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
