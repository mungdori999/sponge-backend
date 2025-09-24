package com.mungdori.sponge.application.post.provided;

import com.mungdori.sponge.domain.owner.Owner;
import com.mungdori.sponge.domain.pet.Pet;
import com.mungdori.sponge.domain.pet.PetFixture;
import com.mungdori.sponge.domain.post.Post;
import com.mungdori.sponge.domain.post.PostFixture;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static com.mungdori.sponge.domain.owner.OwnerFixture.createOwnerRegisterRequest;
import static com.mungdori.sponge.domain.owner.OwnerFixture.createPasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
record PostFinderTest(PostFinder postFinder,PostManager postManager, EntityManager entityManager) {


    @Test
    void findPost() {
        Owner owner = createOwner();
        Pet pet = createPet(owner.getId());

        Post post = postManager.create(PostFixture.createPostCreateRequest(), pet.getId(), owner.getEmail().address());

        entityManager.flush();
        entityManager.clear();

        var found = postFinder.find(post.getId());

        assertThat(found).isEqualTo(post);

    }

    Owner createOwner() {
        Owner owner = Owner.register(createOwnerRegisterRequest(), createPasswordEncoder());

        entityManager.persist(owner);

        entityManager.flush();
        entityManager.clear();

        return owner;
    }

    Pet createPet(Long ownerId) {
        Pet pet = Pet.register(PetFixture.createPetRegisterRequest(), ownerId);

        entityManager.persist(pet);

        entityManager.flush();
        entityManager.clear();

        return pet;
    }


}