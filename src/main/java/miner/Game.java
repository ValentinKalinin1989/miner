package miner;

public class Game {
    private BombField bombField;
    private FlagField flagField;
    private GameState state;

    public Game (int cols, int rows, int bombs) {
        Ranges.setSize(new Coord(cols, rows));
        bombField = new BombField(bombs);
        flagField = new FlagField();
    }

    private void checkWinner() {
        if(state == GameState.PLAYED)
            if(flagField.getCountOfClosedBoxes() == bombField.getTotalBombs()) {
                state = GameState.WINNER;
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
            case OPENED: setOpenedToClosedBoxesAroundNumber(coord);return;
            case FLAGED: return;
            case CLOSED:
                switch (bombField.getBox(coord))
                {
                    case ZERO: openBoxesAround(coord); return;
                    case BOMB: openBomb(coord); return;
                    default  : flagField.setOpenedToBox(coord);
                }
        }
    }

    private void openBomb(Coord bombed) {
        state = GameState.BOMBED;
        flagField.stBombedToBox (bombed);
        for (Coord coord: Ranges.getAllCoords())
            if(bombField.getBox (coord) == Cell.BOMB)
                flagField.setOpenedToClosedBombBox(coord);
            else
                flagField.setNoBombToFlagedSafeBox(coord);
    }

    private void openBoxesAround(Coord coord) {
        flagField.setOpenedToBox(coord);
        for (Coord around: Ranges.getCoordAround(coord))
            openBox(around);
    }

    public void tryToOpenCell(Coord coord) {
        if(gameOver()) return;
        openBox(coord);
        checkWinner();
    }

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
}
