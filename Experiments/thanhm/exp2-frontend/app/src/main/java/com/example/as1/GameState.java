package com.example.as1;

import java.util.Objects;

public class GameState {
    private static GameState instance;

    private int cookieCount = 0;
    private int cookieClickRate = 1;

    int getCookieCount() { return cookieCount; }

    int getCookieClickRate() { return cookieClickRate; }

    void cookieClickAction() { cookieCount += cookieClickRate; }

    // No outside constructor access
    private GameState() { }

    // Instance getter
    static synchronized GameState get() {
        if (instance == null) instance = new GameState();
        return instance;
    }

    @Override
    public int hashCode() { return Objects.hash(cookieCount, cookieClickRate); }
}
