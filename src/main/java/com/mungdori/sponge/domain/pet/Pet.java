package com.mungdori.sponge.domain.pet;

import com.mungdori.sponge.domain.AbstractEntity;
import com.mungdori.sponge.domain.shared.GenderType;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


import static java.util.Objects.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet extends AbstractEntity {

    private String name;

    private String breed;

    private GenderType gender;

    private int age;

    private float weight;


    public static Pet register(PetRegisterRequest registerRequest) {
        Pet pet = new Pet();

        pet.name = requireNonNull(registerRequest.name());
        pet.breed = requireNonNull(registerRequest.breed());
        pet.gender = requireNonNull(registerRequest.gender());
        pet.age = registerRequest.age();
        pet.weight = registerRequest.weight();

        return pet;
    }
}
