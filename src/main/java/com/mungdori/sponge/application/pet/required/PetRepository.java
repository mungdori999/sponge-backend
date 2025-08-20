package com.mungdori.sponge.application.pet.required;

import com.mungdori.sponge.domain.pet.Pet;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PetRepository extends Repository<Pet,Long> {

    Pet save(Pet pet);

    Optional<Pet> findById(Long id);

}
