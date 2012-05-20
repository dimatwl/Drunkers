package ru.spbau.shestavin.drunkers;

/**
 * Classname:
 * User: dimatwl
 * Date: 5/20/12
 * Time: 1:31 PM
 */
public class NoSuchPositionException extends Exception {
    public NoSuchPositionException(String inpLine) {
        super(inpLine);
    }
}
