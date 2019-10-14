package miner.fields;

import java.util.ArrayList;
import java.util.Random;
/**
 * используется для хранения границ игрового поля
 * определяет будет ли ячейка с заданными координатамиахрдится в пределах игрового поля
 * получает список координат смежных ячеек для заданной клетки
 */
public class Ranges {
    private static Coord size;
    private static ArrayList<Coord> allCoords;
    private static Random random = new Random();

    public static void setSize(Coord _size) {
        size = _size;
        allCoords = new ArrayList<>();
        for (int y = 0; y < size.getY(); y++) {
            for (int x = 0; x < size.getX(); x++) {
                allCoords.add(new Coord(x, y));
            }
        }
    }

    public static Coord getSize() {
        return size;
    }
    /**
     * возвращает список всех координат игрового поля
     * @return список координаты
     */
    public static ArrayList<Coord> getAllCoords() {
        return allCoords;
    }
    /**
     * проверяет существует ли данная координата в игровом поле
     * @param coord координата, которую необходимо проверить
     * @return true - если координата в пределах игрового поля, иначе - false
     */
    static boolean inRange (Coord coord) {
        return coord.getX() >= 0 && coord.getX() < size.getX()
                && coord.getY() >= 0 && coord.getY() < size.getY();
    }
    /**
     * возвращает список возможных смежных координат для выбранной коорлинаты
     * @return случайная координата
     */
    static Coord getRandomCoord() {
        return new Coord(random.nextInt(size.getX()),
                         random.nextInt(size.getY()));
    }
    /**
     * возвращает список возможных смежных координат для клетки
     * @param coord - координата ячейки, для которой необходимо получить список координат
     * @return спсиок координат
     */
    public static ArrayList<Coord> getCoordAround(Coord coord) {
        Coord around;
        ArrayList<Coord> listOfCoords = new ArrayList<>();
        for (int x = coord.getX() - 1; x <=coord.getX() + 1; x++)
            for (int y = coord.getY() - 1; y <=coord.getY() + 1; y++)
                if(inRange(around = new Coord(x, y)))
                    if(!around.equals(coord))
                        listOfCoords.add(around);
        return listOfCoords;
    }


}
