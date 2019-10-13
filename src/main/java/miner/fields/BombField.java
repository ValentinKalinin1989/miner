package miner.fields;
/**
 * Внутреннее поле для хранения мин
 * 1) Расставляет мины и хранит их
 * 2) Устанавливает в ячейку число мин в смежных ячейках
 * 3) Хранит число сработавших мин
 */
public class BombField {
    private Field bombMap;
    private int totalBombs;
    private int totalBombed;

    public BombField(int totalBombs) {
        this.totalBombs = totalBombs;
        fixBombsCount();
    }

    public void start() {
        bombMap = new Field(Cell.ZERO);
        this.totalBombed = 0;
        for (int i = 0; i < totalBombs; i++) {
            placeBomb();
        }
    }

    public Cell getBox(Coord coord) {
        return bombMap.get(coord);
    }

    public void incrementTotalBombed() {
        this.totalBombed++;
    }

    public int getTotalBombed() {
        return this.totalBombed;
    }

    public int getTotalBombs() {
        return this.totalBombs;
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
}
