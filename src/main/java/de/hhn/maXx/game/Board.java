package de.hhn.maXx.game;

import de.hhn.maXx.util.Direction;
import de.hhn.maXx.util.FieldState;
import de.hhn.maXx.util.Fraction;
import de.hhn.maXx.util.IntVector2;

import java.util.stream.IntStream;

/**
 * Die Klasse Board beinhaltet alle Methoden, um Spieler zu bewegen,
 * Spielfelder zu befüllen und Informationen zum Spielbrett zu erlangen.
 *
 * @author Lukas Vier, Henri Staudenrausch, Nico Vogel
 * @version 2, 19.12.22
 */

public class Board {
    private final Field[][] grid;
    private IntVector2 whitePos, blackPos;

    public Board() {
        this.grid = new Field[8][8];
        fillField();
        this.whitePos = new IntVector2(3, 2);
        this.blackPos = new IntVector2(4, 5);
        setFieldState(this.whitePos, FieldState.WHITE);
        setFieldState(this.blackPos, FieldState.BLACK);
    }

    public FieldState getFieldState(IntVector2 pos) {
        return this.grid[pos.x][pos.y].getState();
    }

    public void setFieldState(IntVector2 pos, FieldState state) {
        this.grid[pos.x][pos.y].setState(state);
    }

    public Fraction getFraction(IntVector2 pos) {
        if (this.grid[pos.x][pos.y].getState() == FieldState.FRACTION) {
            return this.grid[pos.x][pos.y].getFraction();
        } else {
            return Fraction.ZERO;
        }

    }

    private void fillField() {
        IntStream.range(0, 8).forEach(
            x -> IntStream.range(0, 8).forEach(
                y -> this.grid[x][y] = new Field()
            )
        );
    }

    private boolean movePossible(IntVector2 target) {
        return !posOutOfBounds(target) && !getFieldState(target).equals(FieldState.BLACK) && !getFieldState(target).equals(FieldState.WHITE);
    }

    private static boolean posOutOfBounds(IntVector2 pos) {
        return pos.x > 7 || pos.x < 0 || pos.y > 7 || pos.y < 0;
    }

    public boolean movePlayer(boolean isWhite, Direction direction) {
        IntVector2 target = getNewCoords(isWhite, direction);
        if (movePossible(target)) {
            if (isWhite) {
                if (getFieldState(target).equals(FieldState.FRACTION))
                    Game.getInstance().addScoreWhite(getFraction(target));
                setFieldState(this.whitePos, FieldState.EMPTY);
                setFieldState(target, FieldState.WHITE);
                this.whitePos = target;
            } else {
                if (getFieldState(target).equals(FieldState.FRACTION))
                    Game.getInstance().addScoreBlack(getFraction(target));
                setFieldState(this.blackPos, FieldState.EMPTY);
                setFieldState(target, FieldState.BLACK);
                this.blackPos = target;
            }
            return true;
        }
        return false;
    }

    private IntVector2 getNewCoords(boolean isWhite, Direction direction) {
        IntVector2 pos = isWhite ? this.whitePos : this.blackPos;

        pos = pos.add(switch (direction) {
            case RIGHT -> new IntVector2(1, 0);
            case LEFT -> new IntVector2(-1, 0);
            case DOWN -> new IntVector2(0, 1);
            case UP -> new IntVector2(0, -1);
            case DIAGONAL -> isWhite ? new IntVector2(1, -1) : new IntVector2(-1, 1);
        });
        return pos;
    }
}