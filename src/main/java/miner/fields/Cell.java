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
    /**
     * хранит ссылку на объект для
     * графического представления ячейки
     */
    public Object image;
    /**
     * возвращает ячейку следующую за текущей
     * @return следующая ячейка в enum
     */
    public Cell nextNumberBox(){
        return Cell.values()[this.ordinal() + 1];
    }

    /**
     * возвращает цифровое значение ячейки
     * @return порядковый номер ячейки
     */
    public int getNumber() {
       return this.ordinal();
    }

}
