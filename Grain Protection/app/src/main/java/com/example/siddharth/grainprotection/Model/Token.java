package com.example.siddharth.grainprotection.Model;

/**
 * Created by Siddharth on 24-12-2017.
 */

public class Token {
    private String Token;
    private boolean isServerToken;

    public Token() {
    }

    public Token(String token, boolean isServerToken) {
        Token = token;
        this.isServerToken = isServerToken;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public boolean isServerToken() {
        return isServerToken;
    }

    public void setServerToken(boolean serverToken) {
        isServerToken = serverToken;
    }
}
