package miner.logic;

import miner.conditions.ConditionLose;
import miner.conditions.ConditionWin;
import miner.fields.*;

import java.util.List;

/**
 * отвечает за логику игры
 */
public class Game {
    private BombField bombField;
    private FlagField flagField;
    private GameState state;
    private List<ConditionWin> condWinList;
    private List<ConditionLose> condLoseList;

    /**
     * конструктор объекта Game
     * @param cols - число столбцов игрового поля
     * @param rows - число строк игрового поля
     * @param bombs - число бомб для установки
     * @param condWinList - список условий победы
     * @param condLoseList - список условий проигрыша
     */
    public Game (int cols, int rows, int bombs, List<ConditionWin> condWinList, List<ConditionLose> condLoseList) {
        Ranges.setSize(new Coord(cols, rows));
        bombField = new BombField(bombs);
        flagField = new FlagField();
        this.condWinList = condWinList;
        this.condLoseList = condLoseList;
    }

    /**
     * проверка выполнения условий победы
     */
    private void checkWinner() {
        if(state == GameState.PLAYED)
            for (ConditionWin condWin: condWinList) {
                if(condWin.isWin(this)) {
                    state = GameState.WINNER;
                }
            }
    }
    /**
     * проверка выполнения условий проигрыша
     */
    private void checkLosing() {
        if(state == GameState.PLAYED) {
            for (ConditionLose condLose : condLoseList) {
                if (condLose.isLose(this)) {
                    state = GameState.LOSING;
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
    /**
     * получения текущего статуса игры
     * @return статус игры
     */
    public GameState getState() {
        return state;
    }
    /**
     * запуск игры
     */
    public void start() {
        bombField.start();
        flagField.start();
        state = GameState.PLAYED;
    }
    /**
     * получения ячейки в зависимости от типа OPENED или нет
     * если OPENED - получение ячейки из внутреннего поля BobmField
     * если нет - получение ячейки из внешнего поля FlagField
     * @param coord - координата ячейки
     * @return - необходимая ячейка
     */
    public Cell getBox (Coord coord) {
        if (flagField.getBox(coord) == Cell.OPENED) {
            return bombField.getBox(coord);
        }
        else {
            return flagField.getBox(coord);
        }
    }
    /**
     * обработка попытки открытия ячейки
     * проверка ячеек во внешнем поле BobmField
     *  если:
     *      OPENED - открыть ячйки рядом, если число смежных помеченных ячеек соответствует числлу в ячейке
     *      FLAGED - не делать никаких действий, т.к. помеченную ячейку нельзя открыть
     *      CLOSED - провести проверку во внутреннем поле FlagField
     *          если:
     *              ZERO - запустить функцию (openBoxesAround)открывующую смежные клетки около клетки, не имющей мин рядом
     *              BOMB - запустить функцию (openBomb) открытия бомбы
     * @param coord - координаты клетки
     */
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
    /**
     * открытие клетки с бомбой
     * @param bombed коодинаты клетки с бомбой
     */
    private void openBomb(Coord bombed) {
        bombField.incrementTotalBombed();
        flagField.stBombedToBox (bombed);
        checkLosing();
        checkWinner();
    }
    /**
     * открытие клеток рядок с клеткой, не имющей мин рядом
     * @param coord - координаты клетки
     */
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
    /**
     * проверка условия окончания игры
     * @return true - если игра завершена
     */
    private boolean gameOver() {
        boolean result = false;
        if (state != GameState.PLAYED)
        {
            result = true;
            start();
        }
        return result;
    }

    /**
     *  открытие ячеек, не помечееных флагом, рядом с текущей ячейкой,
     * если число флагов в смежных клетках соответствует числу мин в смежных клетках
     * @param coord - координаты ячейки
     */
    private void setOpenedToClosedBoxesAroundNumber(Coord coord) {
        if(bombField.getBox(coord) != Cell.BOMB)
            if(flagField.getCountOfFlagedBoxesAround(coord) == bombField.getBox(coord).getNumber())
                for (Coord around: Ranges.getCoordAround(coord))
                    if (flagField.getBox(around) == Cell.CLOSED)
                        openBox(around);
    }

    /**
     * получение внутреннего поля с минами
     * @return - поле с минами
     */
    public BombField getBombField() {
        return bombField;
    }
    /**
     * получение внешнего поля
     * @return - внешнее поле
     */
    public FlagField getFlagField() {
        return flagField;
    }
}
