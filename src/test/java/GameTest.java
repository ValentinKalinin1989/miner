import miner.conditions.*;
import miner.fields.BombField;
import miner.fields.Cell;
import miner.fields.Coord;
import miner.logic.Game;
import miner.logic.GameState;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GameTest {

    @Test
    public void whenOpenAllCellsAndWin() {
        List<ConditionWin> listWin = new ArrayList<>();
        listWin.add(new OpenAllCellToWin());
        List<ConditionLose> listLose = new ArrayList<>();
        listLose.add(new OpenThreeBombToLose());
        Game game = new Game(9, 9, 10, listWin, listLose);
        game.start();
        BombField field = game.getBombField();
        for (int i = 0; i < 81; i++) {
            Coord coord = new Coord(i % 9, i / 9);
            if (field.getBox(coord) != Cell.BOMB) {
                game.tryToOpenCell(coord);
            }
        }
        assertThat(game.getState(), is(GameState.WINNER));
    }

    @Test
    public void whenLoseAtferOpenThirdBomb() {
        List<ConditionWin> listWin = new ArrayList<>();
        listWin.add(new OpenAllCellToWin());
        List<ConditionLose> listLose = new ArrayList<>();
        listLose.add(new OpenThreeBombToLose());
        Game game = new Game(9, 9, 10, listWin, listLose);
        game.start();
        GameState stateLose = takeStateAferLose(game, 9, 9, 3);
        assertThat(stateLose, is(GameState.LOSING));
    }

    @Test
    public void whenLoseAtferOpenFirstBomb() {
        List<ConditionWin> listWin = new ArrayList<>();
        listWin.add(new OpenAllCellToWin());
        List<ConditionLose> listLose = new ArrayList<>();
        listLose.add(new OpenOneBombToLose());
        Game game = new Game(8, 8, 10, listWin, listLose);
        game.start();
        GameState stateLose = takeStateAferLose(game, 8, 8, 1);
        assertThat(stateLose, is(GameState.LOSING));
    }

    private GameState takeStateAferLose(Game game, int cols, int rows, int countBombToLose) {
        BombField field = game.getBombField();
        GameState stateLose = null;
        int countOpendBomb = 0;
        for (int i = 0; i < cols * rows; i++) {
            Coord coord = new Coord(i % cols, i / cols);
            if (field.getBox(coord) == Cell.BOMB) {
                countOpendBomb++;
                game.tryToOpenCell(coord);
                if (countOpendBomb == countBombToLose) {
                    stateLose = game.getState();
                }
            }
        }
        return stateLose;
    }
}
