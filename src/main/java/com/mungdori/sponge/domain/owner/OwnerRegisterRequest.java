package com.mungdori.sponge.domain.owner;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record OwnerRegisterRequest(
        @Email
        String email,
        @Size(min = 2, max = 10)
        String nickName,
        @Size(min = 8, max = 100)
        String password
) {
}
