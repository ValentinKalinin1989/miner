package miner.fields;

/**
 * является основой игрового поля
 * хранит массив ячеек поля
 */
class Field {
    private Cell[][] matrix;

    Field(Cell defaultCell) {
        matrix = new Cell[Ranges.getSize().getX()][Ranges.getSize().getY()];
        for (Coord coord: Ranges.getAllCoords())
            matrix[coord.getX()][coord.getY()] = defaultCell;
    }

    Cell get (Coord coord) {
        if(Ranges.inRange (coord))
            return matrix[coord.getX()][coord.getY()];
        return null;
    }

    void set (Coord coord, Cell cell) {
        if(Ranges.inRange (coord))
            matrix[coord.getX()][coord.getY()] = cell;
    }

}
