package de.hhn.maXx.stateMachine;

import de.hhn.maXx.util.Direction;

import static de.hhn.maXx.Game.getInstance;


/**
 * Diese Klasse definiert die Aktion im Zustand: Spieler an der Reihe.
 *
 * @author Nico Vogel
 * @version 1, 19.12.2022
 **/
public class PlayerTurnState implements State{

    boolean isWhite;

    public PlayerTurnState(boolean isWhite) {
        this.isWhite = isWhite;
    }

    @Override
    public boolean isWhitesTurn() {
        return isWhite;
    }

    @Override
    public boolean move(Direction direction) {
        if (getInstance().getBoard().movePlayer(isWhite, direction)){
            getInstance().getStateManager().setCurrentState(new PlayerTurnState(!isWhite));
            return true;
        }
        return false;
    }
}
