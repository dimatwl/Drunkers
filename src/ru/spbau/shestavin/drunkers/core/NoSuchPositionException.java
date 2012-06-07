package ru.spbau.shestavin.drunkers.core;

public class NoSuchPositionException extends Exception {
    public NoSuchPositionException(String inpLine) {
        super(inpLine);
    }
}
