package com.mungdori.sponge.domain.owner;

public class OwnerFixture {

    public static OwnerRegisterRequest createOwnerRegisterRequest(String email) {

        return new OwnerRegisterRequest(email, "nickname", "longsecret");
    }

    public static OwnerRegisterRequest createOwnerRegisterRequest() {
        return createOwnerRegisterRequest("mungdori999@gmail.com");
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
