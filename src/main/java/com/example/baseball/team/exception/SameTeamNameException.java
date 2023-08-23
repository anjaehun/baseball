package com.example.baseball.team.exception;

public class SameTeamNameException extends Throwable {
    public SameTeamNameException(String message) {
        super(message);
    }
}
