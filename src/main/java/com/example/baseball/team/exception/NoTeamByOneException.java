package com.example.baseball.team.exception;

public class NoTeamByOneException extends Throwable {
    public NoTeamByOneException(String message) {
        super(message);
    }
}
