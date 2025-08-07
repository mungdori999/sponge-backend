package com.mungdori.sponge.domain.owner;


public class DuplicateNicknameException extends RuntimeException{
    public DuplicateNicknameException(String message) {
        super(message);
    }
}
