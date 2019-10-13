import miner.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleMiner {

    private static final String LN = System.lineSeparator();
    private Game game;

    public static void main(String[] args) {
        new ConsoleMiner();
    }

    private ConsoleMiner() {
        setImages();
        game = new Game(9, 9, 10);
        game.start();
        setImages();
        printGameInfo();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inputUser = null;
        do {
            printGameField();
            System.out.println();
            try {
                inputUser = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!sendUserInputToGame(inputUser) || game.getState() == GameState.PLAYED);
        printGameField();
        if(game.getState() == GameState.BOMBED) {
            System.out.println(LN + "Вы проиграли");
        }
        if (game.getState() == GameState.PLAYED) {
            System.out.println(LN + "ПОБЕДА!");
        }
    }

    private void setImages() {
        Cell.ZERO.image = "[0]";
        Cell.NUM1.image = "[1]";
        Cell.NUM2.image = "[2]";
        Cell.NUM3.image = "[3]";
        Cell.NUM4.image = "[4]";
        Cell.NUM5.image = "[5]";
        Cell.NUM6.image = "[6]";
        Cell.NUM7.image = "[7]";
        Cell.NUM8.image = "[8]";
        Cell.BOMB.image = "[*]";
        Cell.OPENED.image = "[+]";
        Cell.CLOSED.image = "[ ]";
        Cell.FLAGED.image = "[F]";
        Cell.BOMBED.image = "[@]";
        Cell.NOBOMB.image = "[-]";
    }

    private void printGameField() {
        System.out.print("[ ]|");
        for (int i = 0; i < Ranges.getSize().getX(); i++)
        {
            System.out.printf("|" + i + "|");
        }
        System.out.print(LN + "---");
        for (int i = 0; i < Ranges.getSize().getX(); i++)
        {
            System.out.printf("---");
        }
        for (Coord coord: Ranges.getAllCoords())
        {
            if(coord.getX() % Ranges.getSize().getX() == 0)
            {
                System.out.print(LN + "[" + coord.getY() % Ranges.getSize().getX() + "]" + "|");
            }
            System.out.print(game.getBox(coord).image);
        }
    }

    private void printGameInfo() {
        System.out.println("------------Сапер----------------"
                + LN + "[0] - число мин в соседних ячейках."
                + LN + "[*] - бомба после открытия игрового поля."
                + LN + "[ ] - закрытая ячейка игрового поля."
                + LN + "[F] - установленный флаг на закрытой ячейке."
                + LN + "[@] - взорвавшаяся бомба."
                + LN + "[-] - ячейка ошибочка помеченая флагом (не было бомбы)."
                + LN + "Пример ввовода данных:"
                + LN + "1 2 open - открыть ячеку с координатами х=1, у=2."
                + LN + "1 2 mark - пометить флагом, либо снять метку с ячеки имющей координаты х=1, у=2.");
    }

    private boolean sendUserInputToGame(String userInput) {
        String[] data = userInput.split(" ");
        int x;
        int y;
        String comand;
        try {
            x = Integer.parseInt(data[0]);
        } catch (Exception e) {
            System.out.println("Координата x введена не правильно");
            return false;
        }
        try {
            y = Integer.parseInt(data[1]);
        } catch (Exception e) {
            System.out.println("Координата y введена не правильно");
            return false;
        }
        try {
            comand = data[2];
        } catch (Exception e) {
            System.out.println("Не введена команда");
            return false;
        }
        if(x < 0 || x > (Ranges.getSize().getX() - 1)) {
            System.out.println("Координата х выходит из диапазона значений");
            return false;
        }
        if(y < 0 || y > (Ranges.getSize().getY() - 1)) {
            System.out.println("Координата у выходит из диапазона значений");
            return false;
        }
        if(!comand.equals("open") && !comand.equals("mark")) {
            System.out.println("Введенв не корректнгая команда");
            return false;
        }
        Coord coord = new Coord(x, y);
        if(comand.equals("open")) {
            game.tryToOpenCell(coord);
        }
        if(comand.equals("mark")) {
            game.flagedUnflagedCell(coord);
        }
        return true;
    }
}
