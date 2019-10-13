package miner;

import java.util.ArrayList;
import java.util.Random;

public class Ranges {
    private static Coord size;
    private static ArrayList<Coord> allCoords;
    private static Random random = new Random();

    protected static void setSize(Coord _size) {
        size = _size;
        allCoords = new ArrayList<Coord>();
        for (int y = 0; y < size.getY(); y++)
            for (int x = 0; x < size.getX(); x++)
                allCoords.add(new Coord(x, y));

    }

    public static Coord getSize() {
        return size;
    }

    public static ArrayList<Coord> getAllCoords() {
        return allCoords;
    }

    static boolean inRange (Coord coord) {
        return coord.getX() >= 0 && coord.getX() < size.getX()
                && coord.getY() >= 0 && coord.getY() < size.getY();
    }

    static Coord getRandomCoord() {
        return new Coord(random.nextInt(size.getX()),
                         random.nextInt(size.getY()));
    }

    static ArrayList<Coord> getCoordAround(Coord coord) {
        Coord around;
        ArrayList<Coord> listOfCoords = new ArrayList<Coord>();
        for (int x = coord.getX() - 1; x <=coord.getX() + 1; x++)
            for (int y = coord.getY() - 1; y <=coord.getY() + 1; y++)
                if(inRange(around = new Coord(x, y)))
                    if(!around.equals(coord))
                        listOfCoords.add(around);
        return listOfCoords;
    }


}
