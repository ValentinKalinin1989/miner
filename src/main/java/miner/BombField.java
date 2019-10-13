package miner;

class BombField {
    private Field bombMap;
    private int totalBombs;

    BombField(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    //размещение элементов (bomb)
    void start() {
        bombMap = new Field(Cell.ZERO); //заполнение поля пустыми ячейками
        for (int i = 0; i < totalBombs; i++) {
            placeBomb(); //размещение бомб
        }
    }

    Cell getBox(Coord coord) {
        return bombMap.get(coord);
    }

    private void placeBomb() {
        while (true) {
            Coord coord = Ranges.getRandomCoord();
            if(Cell.BOMB == bombMap.get(coord))
                continue;
            bombMap.set(coord, Cell.BOMB);
            incNumbersAroundBomb(coord);
            break;
        }
    }

    private void incNumbersAroundBomb(Coord coord) {
        for (Coord around: Ranges.getCoordAround(coord))
            if(Cell.BOMB != bombMap.get(around))
                bombMap.set(around, bombMap.get(around).nextNumberBox());
    }

    private void fixBombsCount() {
        int maxBomb = Ranges.getSize().getX() * Ranges.getSize().getY() / 2;
        if (totalBombs > maxBomb)
            totalBombs = maxBomb;
    }

    int getTotalBombs() {
        return this.totalBombs;
    }
}
