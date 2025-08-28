package com.mungdori.sponge.domain.trainer;

import com.mungdori.sponge.domain.owner.PasswordEncoder;
import com.mungdori.sponge.domain.shared.GenderType;

public class TrainerFixture {

    public static TrainerRegisterRequest createTrainerRegisterRequest(String email, String nickname) {

        return new TrainerRegisterRequest(email, nickname, GenderType.MALE, "01012341234", "longsecret", "introduction", 1);
    }

    public static TrainerRegisterRequest createTrainerRegisterRequest() {
        return createTrainerRegisterRequest("mungdori999@gmail.com", "nickname");
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
