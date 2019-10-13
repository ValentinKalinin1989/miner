package miner.logic;

import miner.conditions.ConditionLose;
import miner.conditions.ConditionWin;
import miner.fields.*;

import java.util.List;

public class Game {
    private BombField bombField;
    private FlagField flagField;
    private GameState state;
    private List<ConditionWin> condWinList;
    private List<ConditionLose> condLoseList;

    public Game (int cols, int rows, int bombs, List<ConditionWin> condWinList, List<ConditionLose> condLoseList) {
        Ranges.setSize(new Coord(cols, rows));
        bombField = new BombField(bombs);
        flagField = new FlagField();
        this.condWinList = condWinList;
        this.condLoseList = condLoseList;
    }

    private void checkWinner() {
        if(state == GameState.PLAYED)
            for (ConditionWin condWin: condWinList) {
                if(condWin.isWin(this)) {
                    state = GameState.WINNER;
                }
            }
    }

    private void checkLosing() {
        if(state == GameState.PLAYED) {
            for (ConditionLose condLose : condLoseList) {
                if (condLose.isLose(this)) {
                    state = GameState.BOMBED;
                    for (Coord coord : Ranges.getAllCoords()) {
                        if (bombField.getBox(coord) == Cell.BOMB) {
                            flagField.setOpenedToClosedBombBox(coord);
                        } else {
                            flagField.setNoBombToFlagedSafeBox(coord);
                        }
                    }
                }
            }
        }
    }

    public GameState getState() {
        return state;
    }

    public void start() {
        bombField.start();
        flagField.start();
        state = GameState.PLAYED;
    }

    public Cell getBox (Coord coord) {
        if (flagField.getBox(coord) == Cell.OPENED)
            return bombField.getBox(coord);
        else
            return flagField.getBox(coord);
    }

    private void openBox(Coord coord) {
        switch (flagField.getBox(coord)){
            case OPENED:
                setOpenedToClosedBoxesAroundNumber(coord);
                return;
            case FLAGED:
                return;
            case CLOSED:
                switch (bombField.getBox(coord))
                {
                    case ZERO:
                        openBoxesAround(coord);
                        return;
                    case BOMB:
                        openBomb(coord);
                        return;
                    default  :
                        flagField.setOpenedToBox(coord);
                }
        }
    }

    private void openBomb(Coord bombed) {
        bombField.incrementTotalBombed();
        flagField.stBombedToBox (bombed);
        checkLosing();
        checkWinner();
    }

    private void openBoxesAround(Coord coord) {
        flagField.setOpenedToBox(coord);
        for (Coord around: Ranges.getCoordAround(coord))
            openBox(around);
    }

    /**
     * открытие ячейки пользовыателем
     * @param coord - координаты ячейки
     */
    public void tryToOpenCell(Coord coord) {
        if(gameOver()) return;
        openBox(coord);
        checkWinner();
        checkLosing();
    }

    /**
     * маркировка ячейки пользователем, либо её снятие
     * @param coord- координаты ячейки
     */
    public void flagedUnflagedCell(Coord coord) {
        if(gameOver()) return;
        flagField.toggleFlagedToBox(coord);
    }

    private boolean gameOver() {
        boolean result = false;
        if (state != GameState.PLAYED)
        {
            result = true;
            start();
        }
        return result;
    }

    private void setOpenedToClosedBoxesAroundNumber(Coord coord) {
        if(bombField.getBox(coord) != Cell.BOMB)
            if(flagField.getCountOfFlagedBoxesAround(coord) == bombField.getBox(coord).getNumber())
                for (Coord around: Ranges.getCoordAround(coord))
                    if (flagField.getBox(around) == Cell.CLOSED)
                        openBox(around);
    }

    public BombField getBombField() {
        return bombField;
    }

    public FlagField getFlagField() {
        return flagField;
    }
}
