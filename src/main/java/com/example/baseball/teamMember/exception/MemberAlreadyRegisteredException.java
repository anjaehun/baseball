package com.example.baseball.teamMember.exception;

public class MemberAlreadyRegisteredException extends Throwable {
    public MemberAlreadyRegisteredException(String message) {
        super(message);
    }
}
