package miner;

class FlagField {
    private Field flagMap;
    private int countOfClosedBoxes;

    void start() {
        flagMap = new Field(Cell.CLOSED);
        countOfClosedBoxes = Ranges.getSize().getX() * Ranges.getSize().getY();
    }

    Cell getBox (Coord coord) {
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

    void stBombedToBox(Coord coord) {
        flagMap.set(coord, Cell.BOMBED);
    }

    void setOpenedToClosedBombBox(Coord coord) {
        if (flagMap.get(coord) == Cell.CLOSED)
            flagMap.set(coord, Cell.OPENED);
    }

    void setNoBombToFlagedSafeBox(Coord coord) {
        if (flagMap.get(coord) == Cell.FLAGED)
            flagMap.set(coord, Cell.NOBOMB);
    }

    int getCountOfFlagedBoxesAround(Coord coord) {
        int count = 0;
        for (Coord around: Ranges.getCoordAround(coord))
            if(flagMap.get(around) == Cell.FLAGED)
                count++;

        return count;
    }
}
