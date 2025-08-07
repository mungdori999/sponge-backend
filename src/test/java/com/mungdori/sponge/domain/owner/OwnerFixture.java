package com.mungdori.sponge.domain.owner;

import com.mungdori.sponge.domain.shared.GenderType;

public class OwnerFixture {

    public static OwnerRegisterRequest createOwnerRegisterRequest(String email) {

        return new OwnerRegisterRequest(email, "nickname", GenderType.MALE, "01012341234", "longsecret");
    }

    public static OwnerRegisterRequest createOwnerRegisterRequest() {
        return createOwnerRegisterRequest("mungdori999@gmail.com");
    }

    public static OwnerInfoUpdateRequest createOwnerInfoUpdateRequest(String nickname) {
        return new OwnerInfoUpdateRequest(nickname, GenderType.MALE, "01012341234");
    }

    public static OwnerInfoUpdateRequest createOwnerInfoUpdateRequest() {
        return createOwnerInfoUpdateRequest("nickname");
    }


    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).matches(passwordHash);
            }
        };
    }

}
