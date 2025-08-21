package com.mungdori.sponge;

import com.mungdori.sponge.domain.owner.OwnerInfoUpdateRequest;
import com.mungdori.sponge.domain.owner.OwnerRegisterRequest;
import com.mungdori.sponge.domain.pet.PetInfoUpdateRequest;
import org.assertj.core.api.AssertProvider;
import org.springframework.test.json.JsonPathValueAssert;

import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertThatUtils {

    public static Consumer<AssertProvider<JsonPathValueAssert>> notNull() {
        return value -> assertThat(value).isNotNull();
    }
    public static Consumer<AssertProvider<JsonPathValueAssert>> equalsTo(OwnerRegisterRequest request) {
        return value -> assertThat(value).isEqualTo(request.email());
    }
    public static Consumer<AssertProvider<JsonPathValueAssert>> equalsTo(OwnerInfoUpdateRequest request) {
        return value -> assertThat(value).isEqualTo(request.nickname());
    }

    public static Consumer<AssertProvider<JsonPathValueAssert>> equalsTo(PetInfoUpdateRequest request) {
        return value -> assertThat(value).isEqualTo(request.name());
    }
}
