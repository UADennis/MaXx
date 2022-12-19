package de.hhn.maXx;

import de.hhn.maXx.util.Direction;
import de.hhn.maXx.util.MyIO;

public class InputManager {
    private InputManager() {
    }

    public static Direction getInput(boolean isWhite) {
        Direction dir;
        do {
            String input = MyIO.promptAndRead((isWhite ? "Weiß" : "Schwarz") + " ist an der Reihe. \n Gebe LEFT, RIGHT, UP, DOWN oder DIAGONAL ein, um dich zu bewegen:");
            dir = Direction.fromString(input);
        } while (dir == null);
        return dir;
    }

    private static boolean isViableDirection(String str) {
        return switch (str.toLowerCase()) {
            case "s", "n", "e", "w", "d" -> true;
            default -> false;
        };
    }
}
