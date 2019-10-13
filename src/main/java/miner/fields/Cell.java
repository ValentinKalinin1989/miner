package miner.fields;

/**
 * описание ячейки игрового поля
 */

public enum Cell {
    ZERO,
    NUM1,
    NUM2,
    NUM3,
    NUM4,
    NUM5,
    NUM6,
    NUM7,
    NUM8,
    BOMB,
    OPENED,
    CLOSED,
    FLAGED,
    BOMBED,
    NOBOMB;
    public Object image;
    public Cell nextNumberBox(){
        return Cell.values()[this.ordinal() + 1];
    }
    public int getNumber() {
       return this.ordinal();
    }

}
