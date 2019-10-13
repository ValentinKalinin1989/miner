package miner.conditions;

import miner.logic.Game;

public class OpenAllCellToWin implements ConditionWin {
    @Override
    public boolean isWin(Game game) {
        boolean result = false;
        if(game.getBombField().getTotalBombs() == game.getFlagField().getCountOfClosedBoxes()) {
            result = true;
        }
        if(game.getBombField().getTotalBombed() == (game.getFlagField().getCountOfClosedBoxes() - game.getBombField().getTotalBombed()))
        {
            result = true;
        }
        return result;
    }
}
