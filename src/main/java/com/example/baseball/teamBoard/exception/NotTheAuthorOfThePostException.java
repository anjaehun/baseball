package com.example.baseball.teamBoard.exception;

public class NotTheAuthorOfThePostException extends Throwable {
    public NotTheAuthorOfThePostException(String message) {
        super(message);
    }
}
