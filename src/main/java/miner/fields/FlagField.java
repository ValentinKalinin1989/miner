package miner.fields;

/**
 * хранит внешнее игровое поле
 * состоит из ячеек типа OPENED, CLOSED, FLAGED
 */
public class FlagField {
    /**
     * игровое поле
     */
    private Field flagMap;
    /**
     * число закрытых ячеек(CLOSED)
     */
    private int countOfClosedBoxes;

    public void start() {
        flagMap = new Field(Cell.CLOSED);
        countOfClosedBoxes = Ranges.getSize().getX() * Ranges.getSize().getY();
    }

    public Cell getBox (Coord coord) {
        return flagMap.get(coord);
    }

    public void setOpenedToBox(Coord coord) {
        flagMap.set(coord, Cell.OPENED);
        countOfClosedBoxes--;
    }

    public void toggleFlagedToBox(Coord coord) {
        switch (flagMap.get(coord)){
            case FLAGED: setClosedToBox(coord);
            break;
            case CLOSED: setFlagedToBox(coord);
            break;
        }
    }

    private void setClosedToBox(Coord coord) {
        flagMap.set(coord, Cell.CLOSED);
    }

    private void setFlagedToBox(Coord coord) {
        flagMap.set(coord, Cell.FLAGED);
    }


    public int getCountOfClosedBoxes() {
        return countOfClosedBoxes;
    }

    public void stBombedToBox(Coord coord) {
        flagMap.set(coord, Cell.BOMBED);
    }

    public void setOpenedToClosedBombBox(Coord coord) {
        if (flagMap.get(coord) == Cell.CLOSED) {
            flagMap.set(coord, Cell.OPENED);
        }
    }

    public void setNoBombToFlagedSafeBox(Coord coord) {
        if (flagMap.get(coord) == Cell.FLAGED) {
            flagMap.set(coord, Cell.NOBOMB);
        }
    }

    public int getCountOfFlagedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around: Ranges.getCoordAround(coord)) {
            if (flagMap.get(around) == Cell.FLAGED) {
                count++;
            }
        }
        return count;
    }
}
