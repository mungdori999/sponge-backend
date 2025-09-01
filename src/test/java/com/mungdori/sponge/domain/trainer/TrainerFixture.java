package com.mungdori.sponge.domain.trainer;

import com.mungdori.sponge.domain.owner.PasswordEncoder;
import com.mungdori.sponge.domain.shared.GenderType;

import java.util.ArrayList;
import java.util.List;

public class TrainerFixture {

    public static TrainerRegisterRequest createTrainerRegisterRequest(String email, String nickname) {

        return new TrainerRegisterRequest(email, nickname, GenderType.MALE, "01012341234", "longsecret", "introduction", 1, createHistoryCreateRequest());
    }

    public static TrainerRegisterRequest createTrainerRegisterRequest() {
        return createTrainerRegisterRequest("mungdori999@gmail.com", "nickname");
    }

    public static List<HistoryCreateRequest> createHistoryCreateRequest() {
        List<HistoryCreateRequest> list = new ArrayList<>();
        list.add(new HistoryCreateRequest("title1", "201201", "202002", "description1"));
        list.add(new HistoryCreateRequest("title2", "202003", "202105", "description2"));
        list.add(new HistoryCreateRequest("title3", "202106", "202212", "description3"));
        return list;
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
